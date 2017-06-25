package com.hawkeye.servlet;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.hawkeye.beans.SQLStatsBean;
import com.hawkeye.beans.TraceStatsBean;
import com.hawkeye.db.DerbyDbUtils;
import com.hawkeye.utils.*;




public class TraceDisplayBeanServlet extends HttpServlet{
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
		List<TraceStatsBean> list = DerbyDbUtils.getListofTraceStats();
		/*TraceStatsDisplayBean bean = new TraceStatsDisplayBean();
		//Set<String> set = new TreeSet<String>();
		//for (TraceStatsBean traceStatsBean : list) {
		//	set.add(traceStatsBean.getMethodName());
		//}
		
		List<TraceStatsDisplayBean> displayList = new ArrayList<TraceStatsDisplayBean>();
		//Iterator<String> itr =set.iterator();
		
		//while(itr.hasNext()){

			
			for (int j = 0; j < list.size(); j++)
			{
				bean.setMethodName(list.get(j).getMethodName());
				bean.setAction(list.get(j).getAction());
				bean.setInputParams(list.get(j).getInputParams());
				bean.setReturnVals(list.get(j).getReturnVals());
				bean.setExecTime(list.get(j).getExecTime());
				displayList.add(bean);
			}
			*/

		//List<TraceStatsDisplayBean> displayList = new ArrayList<TraceStatsDisplayBean>();
			//displayList.add((TraceStatsDisplayBean)list);
			
        req.setAttribute("TracestatsList", list);
        RequestDispatcher rd = req.getRequestDispatcher("/TraceDisplayUI.jsp");
        rd.forward(req, res);
    }
}