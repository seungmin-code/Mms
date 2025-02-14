$(function() {
    createBackGroundMap();
    searchStationData();
    markerOnClickEvent();
});

let map;
let clusterSource;
let selectedCluster = null;

function searchStationData() {
    const success = function(response) {
        addMarker(response.data);
    };
    ajaxCall("/gismap", "GET", "", success, "", "");
}

function realTimeDayData(msrstnName) {
    console.log("관측소명 : " + msrstnName);

    const params = {
        msrstnName: msrstnName,
        inqBginMm: getYYYYmmddNDaysAgo(7),
        inqEndMm: getYYYYmmdd()
    }

    const success = function(response) {
        createChart(response.data, "pm10");
        createChart(response.data, "pm25");
    }

    ajaxCallNoBlock("/realTimeDay/fetchApiCallData", "GET", params, success, "", "true");
}

function createChart(data, category) {
    if (data.length > 0) {
        const dataName = category + "Value";
        const chartName = category + "Chart";

        let title = "";
        if (category === "pm10") {
            title = "미세먼지";
        } else {
            title = "초미세먼지";
        }

        const dates = data.map(item => item.msurDt);
        const values = data.map(item => item[dataName] !== null ? parseFloat(item[dataName]) : 0);

        const chartContainer = document.getElementById(chartName);
        const chart = echarts.init(chartContainer);

        const option = {
            title: {
                text: `${title} 농도 데이터`,
                left: 'left'
            },
            tooltip: {
                trigger: 'axis'
            },
            xAxis: {
                type: 'category',
                data: dates,
                axisLabel: {
                    rotate: 45,
                    interval: 0,
                    formatter: function (value) {
                        return value;
                    }
                }
            },
            yAxis: {
                type: 'value',
                name: `농도 (µg/m³)`
            },
            series: [{
                data: values,
                type: 'line',
                smooth: true,
                itemStyle: {
                    color: '#5470c6'
                }
            }]
        };

        chart.setOption(option);
    } else {
        console.error("데이터가 비어 있습니다.");
    }
}


function createBackGroundMap() {
    clusterSource = new ol.source.Cluster({
        distance: 50,
        source: new ol.source.Vector()
    });

    map = new ol.Map({
        target: 'map',
        layers: [
            new ol.layer.Tile({
                source: new ol.source.OSM()
            }),
            new ol.layer.Vector({
                source: clusterSource,
                style: clusterStyleFunction
            })
        ],
        view: new ol.View({
            center: ol.proj.fromLonLat([127.024612, 37.532600]),
            zoom: 10
        })
    });
}

function clusterStyleFunction(feature) {
    const size = feature.get('features').length;
    let color = 'rgba(13, 110, 253, 0.8)';

    if (selectedCluster && feature === selectedCluster) {
        color = 'rgba(255, 0, 0, 0.8)';
    }

    return new ol.style.Style({
        image: new ol.style.Circle({
            radius: 10 + size,
            fill: new ol.style.Fill({ color: color }),
            stroke: new ol.style.Stroke({
                color: 'rgba(10, 80, 200, 0.9)',
                width: 1.5
            })
        }),
        text: new ol.style.Text({
            text: size.toString(),
            font: '12px Noto Sans KR, sans-serif',
            fill: new ol.style.Fill({ color: '#fff' })
        })
    });
}

function addMarker(data) {
    data.forEach(function(data) {
        const coordinate = ol.proj.fromLonLat([data.dm_y, data.dm_x]);

        const markerFeature = new ol.Feature({
            geometry: new ol.geom.Point(coordinate)
        });

        markerFeature.setProperties({
            station_name: data.station_name,
            addr: data.addr,
            item: data.item,
            mang_name: data.mang_name,
            year: data.year
        });

        markerFeature.setStyle(new ol.style.Style({
            image: new ol.style.Icon({
                src: '/css/images/marker.svg',
                scale: 1.5,
                anchor: [0.5, 1]
            })
        }));

        clusterSource.getSource().addFeature(markerFeature);
    });
}

function resetClusterStyle(feature) {
    feature.setStyle(new ol.style.Style({
        image: new ol.style.Circle({
            radius: 10 + feature.get('features').length,
            fill: new ol.style.Fill({ color: 'rgba(13, 110, 253, 0.8)' }),
            stroke: new ol.style.Stroke({
                color: 'rgba(10, 80, 200, 0.9)',
                width: 1.5
            })
        }),
        text: new ol.style.Text({
            text: feature.get('features').length.toString(),
            font: '12px Noto Sans KR, sans-serif',
            fill: new ol.style.Fill({ color: '#fff' })
        })
    }));
}

function markerOnClickEvent() {
    map.on('click', function(event) {
        const feature = map.forEachFeatureAtPixel(event.pixel, function(feature) {
            return feature;
        });

        if (feature) {
            const features = feature.get('features');

            if (features.length === 1) {
                const markerFeature = features[0];
                const markerCoord = markerFeature.getGeometry().getCoordinates();

                map.getView().animate({ center: markerCoord, duration: 400 });

                if (selectedCluster && selectedCluster !== feature) {
                    resetClusterStyle(selectedCluster);
                }

                if (selectedCluster !== feature) {
                    feature.setStyle(new ol.style.Style({
                        image: new ol.style.Circle({
                            radius: 10 + features.length,
                            fill: new ol.style.Fill({ color: 'rgba(255, 0, 0, 0.8)' }),
                            stroke: new ol.style.Stroke({
                                color: 'rgba(200, 0, 0, 0.9)',
                                width: 1.5
                            })
                        }),
                        text: new ol.style.Text({
                            text: features.length.toString(),
                            font: '12px Noto Sans KR, sans-serif',
                            fill: new ol.style.Fill({ color: '#fff' })
                        })
                    }));

                    selectedCluster = feature;
                }

                const station_name = markerFeature.get("station_name");
                const addr = markerFeature.get("addr");
                const item = markerFeature.get("item");
                const year = markerFeature.get("year");
                const mang_name = markerFeature.get("mang_name");

                // 미세먼지 일평균 데이터 조회 및 차트생성
                realTimeDayData(station_name);

                openSideBar(station_name, addr, item, year, mang_name);
            } else {
                alert("측정소 선택 범위를 1까지 좁혀주세요");
            }
        }
    });
}

function openSideBar(station_name, addr, item, year, mang_name) {
    $("#station_name").text(station_name);
    $("#addr").text(addr);
    $("#item").text(item);
    $("#year").text(year);
    $("#mang_name").text(mang_name);
    $("#offcanvasRight").addClass("show");
}

function closeSideBar() {
    $("#offcanvasRight").removeClass("show");
}
