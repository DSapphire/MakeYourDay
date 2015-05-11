package com.duan.test;

import com.duan.model.*;
import com.duan.view.CourseTableView;

public class TestCourseTable {

	public static void main(String[] args) {
		CourseTableView tableView=new CourseTableView(new MyCourseTable());
		tableView.courseTableRepaint();
	}

}
