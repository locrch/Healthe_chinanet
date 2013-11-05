package com.pangu.neusoft.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.pangu.neusoft.core.models.Department;
import com.pangu.neusoft.core.models.Schedule;

public class SortListByItem {
	
	public List<Department> sortDepartmentListByName(List<Department> list){
		if(list!=null)
		 Collections.sort(list, new ComparatorDepartmentUtil());
		 return list;
	}	
	
	public List<Schedule> sortScheduleByDay(List<Schedule> list){
		 if(list!=null)
		 Collections.sort(list, new ComparatorSchedultUtil());
		 return list;
	}	
	
	public List<String> sortScheduleByTime(List<String> list){
		 if(list!=null)
		 Collections.sort(list, new ComparatorSchedultTime());
		 return list;
	}	
	
	
}
class ComparatorDepartmentUtil implements Comparator<Department>
{

    public int compare(Department o1, Department o2) { 
        if (o1.getSortKey().compareTo(o2.getSortKey()) > 0)
        {
            return 1;
        }
        else if (o1.getSortKey().compareTo(o2.getSortKey()) < 0)
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }
    
}

class ComparatorSchedultUtil implements Comparator<Schedule>
{

    public int compare(Schedule o1, Schedule o2) { 
        if (o1.getOutcallDate().compareTo(o2.getOutcallDate()) > 0)
        {
            return 1;
        }
        else if (o1.getOutcallDate().compareTo(o2.getOutcallDate()) < 0)
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }
    
}

class ComparatorSchedultTime implements Comparator<String>
{

    public int compare(String o1, String o2) { 
        if (o1.compareTo(o2) > 0)
        {
            return 1;
        }
        else if (o1.compareTo(o2) < 0)
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }
    
}