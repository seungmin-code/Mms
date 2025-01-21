package com.min.mms.reservation.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ReservationRequest {

    private String check_type;
    private String check_date;
    private String check_time;
    private String device;
    private String check_reason;
    private String other_reason;
    private String assignee;
    private String contact;

    // 점검예약 필요 파라미터 유효성검사
    public void reservationRequestValidate() {
        // 점검 날짜 필수
        if (check_date == null || check_date.isEmpty()) {
            throw new IllegalArgumentException("점검 날짜는 필수 항목입니다.");
        }

        // 점검 시간 필수
        if (check_time == null || check_time.isEmpty()) {
            throw new IllegalArgumentException("점검 시간은 필수 항목입니다.");
        }

        // 점검 종류 필수
        if (check_type == null || check_type.isEmpty()) {
            throw new IllegalArgumentException("점검 종류는 필수 항목입니다.");
        }

        // 점검 대상 필수
        if (device == null || device.isEmpty()) {
            throw new IllegalArgumentException("점검 대상은 필수 항목입니다.");
        }

        // 점검 사유 필수
        if (check_reason == null || check_reason.isEmpty()) {
            throw new IllegalArgumentException("점검 사유는 필수 항목입니다.");
        }

        // 기타 사유 필수 (점검 사유가 기타일 경우)
        if ("other".equals(check_reason) && (other_reason == null || other_reason.isEmpty())) {
            throw new IllegalArgumentException("기타 점검 사유는 필수 항목입니다.");
        }

        // 담당자명 필수
        if (assignee == null || assignee.isEmpty()) {
            throw new IllegalArgumentException("담당자명은 필수 항목입니다.");
        }

        // 담당자 연락처 필수
        if (contact == null || contact.isEmpty()) {
            throw new IllegalArgumentException("담당자 연락처는 필수 항목입니다.");
        }
    }
}
