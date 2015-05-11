package com.duan.util;

import java.util.Scanner;

import javax.swing.JOptionPane;

import com.duan.model.MyCourse;
import com.duan.model.MyPriority;
import com.duan.model.MyTime;

public class CreateAndRemove {
	
	public static MyCourse createCourseByDialog() {
		MyCourse course = new MyCourse();
		String name, teacher, ctype, place, time,weekType;
		name = JOptionPane.showInputDialog(null, "请输入课程名", "课程信息输入",
				JOptionPane.INFORMATION_MESSAGE);
		course.setCname(name);
		teacher = JOptionPane.showInputDialog(null, "请输入任课教师", "课程信息输入",
				JOptionPane.INFORMATION_MESSAGE);
		course.setTeacher(teacher);
		ctype = JOptionPane.showInputDialog(null, "请输入课程类型：必修、、限选、任选、其他",
				"课程信息输入", JOptionPane.INFORMATION_MESSAGE);
		course.setCtype(ctype);
		weekType = JOptionPane.showInputDialog(null, "请输入课程类型：单周、双周、全周",
				"课程信息输入", JOptionPane.INFORMATION_MESSAGE);
		course.setWeekType(weekType);
		place = JOptionPane.showInputDialog(null, "请输入上课地点", "课程信息输入",
				JOptionPane.INFORMATION_MESSAGE);
		course.setPlace(place);
		do{
			time = JOptionPane.showInputDialog(null,
					"请输入上课时间：11表示周一第一节，54表示周五第四节，以此类推,连堂课或者多堂课请多次创建", "课程信息输入",
					JOptionPane.INFORMATION_MESSAGE);
		}while(!course.setTime(time));
		return course;
	}
	
}
