package com.api.btlwsandroid.service;

import com.api.btlwsandroid.dao.entity.*;
import com.api.btlwsandroid.dao.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Autowired
    private SubjectGroupRepository subjectGroupRepository;

    @Autowired
    private PracticeGroupRepository practiceGroupRepository;

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    private static final String SCHOOL_TIME_START = "00:00 04/05/2020";
    private static final Integer SHIFT1 = 1;
    private static final Integer SHIFT2 = 2;
    private static final Integer PRACTICE_SHIFT = 3;
    @Override
    public Map<Integer, List<DaySchedule>> getSchedulesOfStudent(Integer studentId) {
        Map<Integer, List<DaySchedule>> timetable = null;
        List<StudentCourse> studentCourses = null;
        List<PracticeGroup> practiceGroups = new ArrayList<>();
        try {
            timetable = new HashMap<>();
            List<Schedule> schedules = scheduleRepository.findAllSubjectGroupScheduleOfStudent(studentId + "");

            boolean[] check = new boolean[162];

            for (int i = 1; i <= 10; i++) {
                List<DaySchedule> daySchedules = new ArrayList<>();
                for (int j = 2; j <= 8; j++) {
                    List<ScheduleCourse> scheduleCourseList = new ArrayList<>();
                    Arrays.fill(check, false);
                    for (Schedule schedule : schedules) {
                        if (schedule.getPracticeGroup().getWeekPractice() != null && schedule.getPracticeGroup().getWeekPractice().contains(i + "")) {
                            if (schedule.getPracticeGroup().getDayPractice() == j)
                                scheduleCourseList.add(new ScheduleCourse(schedule, PRACTICE_SHIFT, null));
                        }
                        if (schedule.getPracticeGroup().getSubjectGroup().getWeek1() != null && !check[schedule.getPracticeGroup().getSubjectGroup().getId()] && schedule.getPracticeGroup().getSubjectGroup().getWeek1().contains(i + "")){
                            if (schedule.getPracticeGroup().getSubjectGroup().getDay() == j){
                                check[schedule.getPracticeGroup().getSubjectGroup().getId()] = true;
                                //

                                scheduleCourseList.add(new ScheduleCourse(schedule, SHIFT1, null));

                                if (schedule.getPracticeGroup().getSubjectGroup().getWeek2() != null && schedule.getPracticeGroup().getSubjectGroup().getWeek2().contains(i + ""))
                                    scheduleCourseList.add(new ScheduleCourse(schedule, SHIFT2, null));
                            }

                        }
//                        else if (schedule.getPracticeGroup().getSubjectGroup().getWeek2() != null && schedule.getPracticeGroup().getSubjectGroup().getWeek2().contains(i + "")){
//                            if (schedule.getPracticeGroup().getSubjectGroup().getDay() == j){
//                                scheduleCourseList.add(new ScheduleCourse(schedule, SHIFT2));
//                            }
//                        }
                    }
                    daySchedules.add(new DaySchedule(j, scheduleCourseList));
                }
                timetable.put(i, daySchedules);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return timetable;
    }

    @Override
    public ScheduleCourse getCurrentScheduleOfStudent(String studentId) {
        List<Schedule> schedules = new ArrayList<>();
        List<Shift> shifts;
        List<Shift> practiceShifts;
        ScheduleCourse scheduleCourse = null;
        Integer type_shift = null;
        try {

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
            LocalDateTime now = LocalDateTime.now();
            String timeNow = dtf.format(now);

            DayOfWeek dayOfWeek = now.getDayOfWeek();
            int day = dayOfWeek.getValue() + 1;

            int hour = Integer.parseInt(timeNow.substring(0, 2));
            Long week = calculateBetwenDateAndDate(SCHOOL_TIME_START, timeNow) / 7;
            week = week % 7 == 0 ? week : week + 1;
            //System.out.println("day: " + day + " hour: " + hour + " week: " + week);
            ArrayList<Integer> shift = checkCurrentLesson(hour);
            ArrayList<Integer> practiceShift = checkCurrentPracticeLesson(hour);
            shifts = shiftRepository.findAllByStartLessonAndEndLesson(shift.get(0), shift.get(1));
            practiceShifts = shiftRepository.findAllByStartLessonAndEndLesson(practiceShift.get(0), practiceShift.get(1));
            shifts.addAll(practiceShifts);
            System.out.println(shifts);
            for (Shift s : shifts) {
                System.out.println(s);
                System.out.println("day: " + day + " shift: " + s.getId() + " week: " + week);
                List<Schedule> schedules_practice = scheduleRepository.findAllCurrentSchedulePractice(studentId, day + "", week + "", s.getId() + "");
                List<Schedule> schedules_shift1 = scheduleRepository.findAllCurrentScheduleShift1(studentId, day + "", week + "", s.getId() + "");
                List<Schedule> schedules_shift2 = scheduleRepository.findAllCurrentScheduleShift2(studentId, day + "", week + "", s.getId() + "");
                //System.out.println(schedules1);
                if (schedules_shift1 != null && schedules_shift1.size() > 0) {
                    schedules.addAll(schedules_shift1);
                    System.out.println("dsadsadsads " + schedules);
                    if (schedules.size() > 0 && schedules.get(0).getPracticeGroup().getDayPractice() != null && schedules.get(0).getPracticeGroup().getDayPractice() == day &&schedules.get(0).getPracticeGroup().getPracticeShift() != null && schedules.get(0).getPracticeGroup().getPracticeShift().getId().equals(s.getId())) {
                        type_shift = PRACTICE_SHIFT;
                    }
                    else if (schedules.size() > 0 && schedules.get(0).getPracticeGroup().getSubjectGroup().getDay() == day && schedules.get(0).getPracticeGroup().getSubjectGroup().getShift1().getId().equals(s.getId())) {
                        type_shift = SHIFT1;
                    }
                    else if (schedules.size() > 0 && schedules.get(0).getPracticeGroup().getSubjectGroup().getDay() == day && schedules.get(0).getPracticeGroup().getSubjectGroup().getShift2() != null && schedules.get(0).getPracticeGroup().getSubjectGroup().getShift2().getId().equals(s.getId())) {
                        type_shift = SHIFT2;
                    }
                }
                if (schedules_shift2 != null && schedules_shift2.size() > 0) {
                    schedules.addAll(schedules_shift2);
                    if (schedules.size() > 0 && schedules.get(0).getPracticeGroup().getDayPractice() != null && schedules.get(0).getPracticeGroup().getDayPractice() == day &&schedules.get(0).getPracticeGroup().getPracticeShift() != null && schedules.get(0).getPracticeGroup().getPracticeShift().getId().equals(s.getId())) {
                        type_shift = PRACTICE_SHIFT;
                    }
                    else if (schedules.size() > 0 && schedules.get(0).getPracticeGroup().getSubjectGroup().getDay() == day && schedules.get(0).getPracticeGroup().getSubjectGroup().getShift1().getId().equals(s.getId())) {
                        type_shift = SHIFT1;
                    }
                    else if (schedules.size() > 0 && schedules.get(0).getPracticeGroup().getSubjectGroup().getDay() == day && schedules.get(0).getPracticeGroup().getSubjectGroup().getShift2() != null && schedules.get(0).getPracticeGroup().getSubjectGroup().getShift2().getId().equals(s.getId())) {
                        type_shift = SHIFT2;
                    }
                }
                if (schedules_practice != null && schedules_practice.size() > 0) {
                    schedules.addAll(schedules_practice);
                    if (schedules.size() > 0 && schedules.get(0).getPracticeGroup().getDayPractice() != null && schedules.get(0).getPracticeGroup().getDayPractice() == day &&schedules.get(0).getPracticeGroup().getPracticeShift() != null && schedules.get(0).getPracticeGroup().getPracticeShift().getId().equals(s.getId())) {
                        type_shift = PRACTICE_SHIFT;
                    }
                    else if (schedules.size() > 0 && schedules.get(0).getPracticeGroup().getSubjectGroup().getDay() == day && schedules.get(0).getPracticeGroup().getSubjectGroup().getShift1().getId().equals(s.getId())) {
                        type_shift = SHIFT1;
                    }
                    else if (schedules.size() > 0 && schedules.get(0).getPracticeGroup().getSubjectGroup().getDay() == day && schedules.get(0).getPracticeGroup().getSubjectGroup().getShift2() != null && schedules.get(0).getPracticeGroup().getSubjectGroup().getShift2().getId().equals(s.getId())) {
                        type_shift = SHIFT2;
                    }
                }

            }
            //
            List<Attendance> attendances = attendanceRepository.findAllAttendanceOfScheduleOfStudent(studentId);
            for(Attendance attendance: attendances){
                if(attendance.getType_schedule().equals(type_shift) && calculateBetwenDateAndDate(attendance.getTime(), timeNow) < 1){
                    scheduleCourse = new ScheduleCourse(schedules.get(schedules.size() - 1), type_shift, true);
                    return scheduleCourse;
                }
            }
            scheduleCourse = new ScheduleCourse(schedules.get(schedules.size() - 1), type_shift, false);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return scheduleCourse;
    }

    public static Boolean isLeapYear(int year) {
        boolean isLeap = false;
        if (year % 4 == 0)//chia hết cho 4 là năm nhuận
        {
            if (year % 100 == 0)
            //nếu vừa chia hết cho 4 mà vừa chia hết cho 100 thì không phải năm nhuận
            {
                if (year % 400 == 0)//chia hết cho 400 là năm nhuận
                    isLeap = true;
                else
                    isLeap = false;//không chia hết cho 400 thì không phải năm nhuận
            } else//chia hết cho 4 nhưng không chia hết cho 100 là năm nhuận
                isLeap = true;
        }
        return isLeap;
    }

    public static Integer calculateDayOfMonth(int month, int year) {
        int day;
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) day = 31;
        else if (month == 2 && isLeapYear(year)) day = 29;
        else if (month == 2 && !isLeapYear(year)) day = 28;
        else day = 30;
        return day;
    }

    public static Long calculateBetwenDateAndDate(String date1, String date2) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");

        Date firstDate = sdf.parse(date1);
        Date secondDate = sdf.parse(date2);
        System.out.println(firstDate + " " + secondDate);
        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());

        return diffInMillies / (24 * 60 * 60 * 1000);
    }

    public static ArrayList<Integer> checkCurrentLesson(int hour) {
        ArrayList<Integer> lessonArrayList = null;
        if (hour >= 7 && hour < 9) lessonArrayList = new ArrayList<Integer>(Arrays.asList(1, 2));
        else if (hour >= 9 && hour < 11) lessonArrayList = new ArrayList<Integer>(Arrays.asList(3, 4));
        else if (hour >= 12 && hour < 14) lessonArrayList = new ArrayList<Integer>(Arrays.asList(5, 6));
        else if (hour >= 14 && hour < 16) lessonArrayList = new ArrayList<Integer>(Arrays.asList(7, 8));
        else if (hour >= 16 && hour < 18) lessonArrayList = new ArrayList<Integer>(Arrays.asList(9, 10));
        else if (hour >= 18 && hour < 20) lessonArrayList = new ArrayList<Integer>(Arrays.asList(11, 12));

        return lessonArrayList;
    }

    public static ArrayList<Integer> checkCurrentPracticeLesson(int hour) {
        ArrayList<Integer> lessonArrayList = null;
        if (hour >= 7 && hour < 11) lessonArrayList = new ArrayList<Integer>(Arrays.asList(1, 4));
        else if (hour >= 12 && hour < 16) lessonArrayList = new ArrayList<Integer>(Arrays.asList(5, 8));
        else if (hour >= 16 && hour < 20) lessonArrayList = new ArrayList<Integer>(Arrays.asList(9, 12));

        return lessonArrayList;
    }
//    public static void main(String[] args) {
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
//        LocalDateTime now = LocalDateTime.now();
//        String timeNow = dtf.format(now);
//
//        DayOfWeek dayOfWeek = now.getDayOfWeek();
//        int day = dayOfWeek.getValue();
//        System.out.println(day);
//
//    }

}
