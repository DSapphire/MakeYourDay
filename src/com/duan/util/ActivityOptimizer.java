package com.duan.util;

import java.util.ArrayList;
import java.util.Calendar;

import com.duan.model.MyPriority;
import com.duan.model.MyTask;
import com.duan.model.MyTaskList;

public class ActivityOptimizer {
	Calendar today=Calendar.getInstance();
	private int days=0;
	private int max=5;//优先级增加最大值
	//读取写入登录日志
	public boolean log(){
		Calendar date=SaveAndRead.readLog();
		SaveAndRead.writeLog(today);
		if(date!=null){
			days=today.get(Calendar.DAY_OF_YEAR)-date.get(Calendar.DAY_OF_YEAR);
			return true;
		}
		return false;
	}
	//最简单的优化,过了一天后任务紧急重要程度都增加，这部分有待优化
	public void optimize(MyTaskList tlist){
		ArrayList<MyTask> list=tlist.getTaskList();
		if(days!=0&&list!=null&&list.size()>0){
			for(MyTask task:list){
				MyPriority p=task.getPriority();
				p.addE(days);
				p.addI(days);
				Calendar ddl=task.getDeadLine();
				if(today.get(Calendar.DAY_OF_YEAR)
						==ddl.get(Calendar.DAY_OF_YEAR)){
					p.addE(max);
					p.addI(max);
				}
			}
		}
	}
}
