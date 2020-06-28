package com.api.btlwsandroid.service;

import com.api.btlwsandroid.dao.entity.*;
import com.api.btlwsandroid.dao.repo.*;
import javafx.util.Pair;
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

    private static final String SCHOOL_TIME_START = "00:00 04/05/2020";

    @Override
    public List<Schedule> getSchedulesOfStudent(Integer studentId) {
        List<Schedule> schedules = null;
        List<StudentCourse> studentCourses = null;
        List<PracticeGroup> practiceGroups = new ArrayList<>();
        try {
            schedules = scheduleRepository.findAllSubjectGroupScheduleOfStudent(studentId + "");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return schedules;
    }

    @Override
    public Pair<Schedule, Boolean> getCurrentScheduleOfStudent(String studentId) {
        List<Schedule> schedules = new ArrayList<>();
        List<Shift> shifts;
        List<Shift> practiceShifts;
        Pair<Schedule, Boolean> scheduleBooleanPair = null;
        boolean isPratice = false;
        try {

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
            LocalDateTime now = LocalDateTime.now();
            String timeNow = dtf.format(now);

            DayOfWeek dayOfWeek = now.getDayOfWeek();
            int day = dayOfWeek.getValue() + 1;

            int hour = Integer.parseInt(timeNow.substring(0, 2));
            Long week = calculateBetwenAnotherdateAndNow(SCHOOL_TIME_START) / 7;
            week = week % 7 == 0 ? week : week + 1;
            //System.out.println("day: " + day + " hour: " + hour + " week: " + week);
            ArrayList<Integer> shift = checkCurrentLesson(hour);
            ArrayList<Integer> practiceShift  = checkCurrentPracticeLesson(hour);
            shifts = shiftRepository.findAllByStartLessonAndEndLesson(shift.get(0), shift.get(1));
            practiceShifts = shiftRepository.findAllByStartLessonAndEndLesson(practiceShift.get(0), practiceShift.get(1));
            shifts.addAll(practiceShifts);
            System.out.println(shifts);
            for (Shift s : shifts) {
                System.out.println(s);
                System.out.println("day: " + day + " shift: " + s.getId() + " week: " + week);
                List<Schedule> schedules1 = scheduleRepository.findCurrentScheduleOfStudent(studentId, day + "", week + "", s.getId() + "");
                System.out.println(schedules1);
                if (schedules1 != null && schedules1.size() > 0) {
                    schedules.addAll(schedules1);
                    if (schedules.size() > 0 && schedules.get(0).getPracticeGroup().getDayPractice() == day && schedules.get(0).getPracticeGroup().getPracticeShift().getId().equals(s.getId())) {
                        isPratice = true;
                    }
                }

            }
            //

            scheduleBooleanPair = new Pair<>(schedules.get(0), isPratice);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return scheduleBooleanPair;
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

    public static Long calculateBetwenAnotherdateAndNow(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");

        Date firstDate = sdf.parse(date);
        Date secondDate = new Date(System.currentTimeMillis());
        System.out.println(firstDate + " " + secondDate);
        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());

        return diffInMillies / (24 * 60 * 60 * 1000);
    }

    public static ArrayList<Integer> checkCurrentLesson(int hour) {
        ArrayList<Integer> lessonArrayList = null;
        if (hour >= 7 && hour < 9) lessonArrayList = new ArrayList<Integer>(Arrays.asList(1,2));
        else if (hour >= 9 && hour < 11) lessonArrayList = new ArrayList<Integer>(Arrays.asList(3,4));
        else if (hour >= 12 && hour < 14) lessonArrayList = new ArrayList<Integer>(Arrays.asList(5,6));
        else if (hour >= 14 && hour < 16) lessonArrayList = new ArrayList<Integer>(Arrays.asList(7,8));
        else if (hour >= 16 && hour < 18) lessonArrayList = new ArrayList<Integer>(Arrays.asList(9,10));
        else if (hour >= 18 && hour < 20) lessonArrayList = new ArrayList<Integer>(Arrays.asList(11,12));

        return lessonArrayList;
    }

    public static ArrayList<Integer> checkCurrentPracticeLesson(int hour) {
        ArrayList<Integer> lessonArrayList = null;
        if (hour >= 7 && hour < 11) lessonArrayList = new ArrayList<Integer>(Arrays.asList(1,4));
        else if (hour >= 12 && hour < 16) lessonArrayList = new ArrayList<Integer>(Arrays.asList(5,8));
        else if (hour >= 16 && hour < 20) lessonArrayList = new ArrayList<Integer>(Arrays.asList(9,12));

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
