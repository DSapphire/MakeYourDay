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
		name = JOptionPane.showInputDialog(null, "������γ���", "�γ���Ϣ����",
				JOptionPane.INFORMATION_MESSAGE);
		course.setCname(name);
		teacher = JOptionPane.showInputDialog(null, "�������ον�ʦ", "�γ���Ϣ����",
				JOptionPane.INFORMATION_MESSAGE);
		course.setTeacher(teacher);
		ctype = JOptionPane.showInputDialog(null, "������γ����ͣ����ޡ�����ѡ����ѡ������",
				"�γ���Ϣ����", JOptionPane.INFORMATION_MESSAGE);
		course.setCtype(ctype);
		weekType = JOptionPane.showInputDialog(null, "������γ����ͣ����ܡ�˫�ܡ�ȫ��",
				"�γ���Ϣ����", JOptionPane.INFORMATION_MESSAGE);
		course.setWeekType(weekType);
		place = JOptionPane.showInputDialog(null, "�������Ͽεص�", "�γ���Ϣ����",
				JOptionPane.INFORMATION_MESSAGE);
		course.setPlace(place);
		do{
			time = JOptionPane.showInputDialog(null,
					"�������Ͽ�ʱ�䣺11��ʾ��һ��һ�ڣ�54��ʾ������Ľڣ��Դ�����,���ÿλ��߶��ÿ����δ���", "�γ���Ϣ����",
					JOptionPane.INFORMATION_MESSAGE);
		}while(!course.setTime(time));
		return course;
	}
	
}
