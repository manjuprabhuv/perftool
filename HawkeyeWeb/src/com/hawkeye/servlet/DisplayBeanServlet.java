package com.hawkeye.servlet;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.hawkeye.beans.SQLStatsBean;
import com.hawkeye.db.DerbyDbUtils;
import com.hawkeye.utils.*;




public class DisplayBeanServlet extends HttpServlet{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException{
        
		//ArrayList al = new ArrayList();
		
		/*SQLStatsBean s = new SQLStatsBean();
		s.setSqlStatment("Select * from test");
		s.setExecTime(1000);
		s.setFailedFlag("N");
		s.setTimestamp(new Timestamp(1000));*/
		List<SQLStatsBean> list = DerbyDbUtils.getListofSqlStats();
		Set<String> set = new TreeSet<String>();
		for (SQLStatsBean sqlStatsBean : list) {
			set.add(sqlStatsBean.getSqlStatment());
		}
		
		Iterator<String> itr =set.iterator();
		List<SQLStatsDisplayBean> displayList = new ArrayList<SQLStatsDisplayBean>();
		
		while(itr.hasNext()){
			String sqlVal = itr.next();
			long count=0;
			long failedCount=0;
			long totalExecTime=0;
			long highestExecTime=0;
			SQLStatsDisplayBean bean = new SQLStatsDisplayBean();
			Timestamp lasttimestamp = null;
			Timestamp highestExecTimeStamp = null;
			for (int j = 0; j < list.size(); j++) {
				System.out.println(sqlVal);
				System.out.println(list.get(j).getSqlStatment());
				if(list.get(j).getSqlStatment().equalsIgnoreCase(sqlVal)){
					count++;
					
					totalExecTime = totalExecTime +list.get(j).getExecTime();
					if("Y".equalsIgnoreCase(list.get(j).getFailedFlag())){
						failedCount++;
					}
					if(lasttimestamp==null){
						lasttimestamp = list.get(j).getTimestamp();
					}else{
						if(list.get(j).getTimestamp().after(lasttimestamp)){
							lasttimestamp = list.get(j).getTimestamp();
						}
					}
					if(highestExecTime==0){
						highestExecTime = list.get(j).getExecTime();
						highestExecTimeStamp = list.get(j).getTimestamp();
					}else{
						if(list.get(j).getExecTime()>highestExecTime){
							highestExecTime = list.get(j).getExecTime();
							highestExecTimeStamp = list.get(j).getTimestamp();
						}
					}
					
				}			
				
			}
			bean.setAvergeExecTime(totalExecTime/count);
			bean.setSqlString(sqlVal);
			bean.setCount(count);
			bean.setFailedCount(failedCount);
			bean.setTimestamp(lasttimestamp);
			bean.setWorstExecTime(highestExecTime);
			bean.setWorstExecTimeStamp(highestExecTimeStamp);
			displayList.add(bean);
			
		}
		
		
        
      //  req.setAttribute("SQLdata", s);
        req.setAttribute("SQLstatsList", displayList);
        RequestDispatcher rd = req.getRequestDispatcher("/SQLDisplayUI.jsp");
        rd.forward(req, res);
    }
}