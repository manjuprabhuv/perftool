<%@page language="java" import="java.util.*,com.hawkeye.db.DerbyDbUtils,com.hawkeye.beans.*,com.hawkeye.utils.*,com.google.gson.*" %>
<%
	List<List<TraceStatsBean>> listMain = DerbyDbUtils
			.getListofTraceStats();

	String str[] = null;
	if (listMain != null && listMain.size() > 0) {
		str = new String[listMain.size()];
		for (int h = 0; h < listMain.size(); h++) {
			List<TraceStatsBean> list = listMain.get(h);
			if (list != null && list.size() > 0) {
				GenericTree<TraceStatsBean> tree = new GenericTree<TraceStatsBean>();
				GenericTreeNode<TraceStatsBean> root = new GenericTreeNode<TraceStatsBean>();
				root.setData(list.get(0));
				tree.setRoot(root);
				GenericTreeNode<TraceStatsBean> currentNode = root;
				GenericTreeNode<TraceStatsBean> parentNode = root;
				for (int i = list.size()-1; i > 0; i--) {
					String str1 = list.get(i).getMethodName();
					String str2 = list.get(0).getMethodName();
					if (str1.equalsIgnoreCase(str2)
							&& list.get(i).getAction()
									.equalsIgnoreCase("EXITING")) {

						list.get(0).setReturnVals(
								list.get(i).getReturnVals());
						list.get(0).setExecTime(
								list.get(i).getExecTime());
					}
				}

				for (int i = 1; i < list.size(); i++) {
					if (list.get(i).getAction()
							.equalsIgnoreCase("ENTERING")) {
						GenericTreeNode<TraceStatsBean> newchild = new GenericTreeNode<TraceStatsBean>();

						// to get the return value
						for (int j = i; j < list.size(); j++) {
							String str1 = list.get(i).getMethodName();
							String str2 = list.get(j).getMethodName();
							if (str1.equalsIgnoreCase(str2)
									&& list.get(j)
											.getAction()
											.equalsIgnoreCase("EXITING")) {

								list.get(i).setReturnVals(
										list.get(j).getReturnVals());
								list.get(i).setExecTime(
										list.get(j).getExecTime());
							}
						}
						newchild.setData(list.get(i));
						currentNode.addChild(newchild);
						parentNode = currentNode;
						currentNode = newchild;
					} else if (list.get(i).getAction()
							.equalsIgnoreCase("EXITING")) {
						// currentNode.
						currentNode = parentNode;
					}
				}
				if (tree.getRoot() != null
						&& tree.getRoot().getData() != null) {
					TraceStatsBean rootBean = tree.getRoot().getData();
					createJsontree(tree.getRoot(), rootBean);
					String jsonData = "";

					Gson gson = new Gson();
					jsonData = gson.toJson(rootBean);
					str[h] = jsonData;
					//System.out.println(jsonData);
				}

			}
		}
	}
	// tree.buildWithDepth(GenericTreeTraversalOrderEnum.PRE_ORDER);
	// tree.getRoot().getChildren().toArray()

	String jsonReturn = "[";
	for (int i = 0; i < str.length; i++) {

		jsonReturn = jsonReturn + str[i];

		if (i != str.length - 1) {
			jsonReturn = jsonReturn + ",";
		}
	}
	jsonReturn = jsonReturn + "]";
	//String str = "[{\"methodName\":\"execution(protected void com.sample.testapp.TraceTestServlet.doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse))\",\"execTime\":0,\"action\":\"ENTERING\",\"inputParams\":\"org.apache.catalina.connector.RequestFacade@289c9d1d , org.apache.catalina.connector.ResponseFacade@420eefbf\",\"returnVals\":\"null\",\"requestId\":7932051,\"children\":[{\"methodName\":\"execution(public com.sample.testapp.SampleObject com.sample.testapp.HelperClass.doCallMethod(java.lang.String, int))\",\"execTime\":617,\"action\":\"ENTERING\",\"inputParams\":\"string , 12500\",\"returnVals\":\"com.sample.testapp.SampleObject@33fed2dd\",\"requestId\":7932051,\"children\":[{\"methodName\":\"execution(public void com.sample.testapp.SampleObject.setIntegerVal(int))\",\"execTime\":0,\"action\":\"ENTERING\",\"inputParams\":\"225\",\"returnVals\":\"null\",\"requestId\":7932051,\"children\":[]},{\"methodName\":\"execution(public void com.sample.testapp.SampleObject.setList(java.util.List))\",\"execTime\":0,\"action\":\"ENTERING\",\"inputParams\":\"[]\",\"returnVals\":\"null\",\"requestId\":7932051,\"children\":[]},{\"methodName\":\"execution(public void com.sample.testapp.SampleObject.setMap(java.util.Map))\",\"execTime\":0,\"action\":\"ENTERING\",\"inputParams\":\"{}\",\"returnVals\":\"null\",\"requestId\":7932051,\"children\":[]},{\"methodName\":\"execution(public void com.sample.testapp.SampleObject.setStrvalue(java.lang.String))\",\"execTime\":0,\"action\":\"ENTERING\",\"inputParams\":\"StringValue\",\"returnVals\":\"null\",\"requestId\":7932051,\"children\":[]},{\"methodName\":\"execution(public java.lang.String com.sample.testapp.HelperClass.doCallMethod2(com.sample.testapp.SampleObject))\",\"execTime\":606,\"action\":\"ENTERING\",\"inputParams\":\"com.sample.testapp.SampleObject@33fed2dd\",\"returnVals\":\"called-1\",\"requestId\":7932051,\"children\":[{\"methodName\":\"execution(public void com.sample.testapp.HelperClass.slowMethod())\",\"execTime\":600,\"action\":\"ENTERING\",\"inputParams\":\"\",\"returnVals\":\"null\",\"requestId\":7932051,\"children\":[]}]}]}]}, {\"methodName\":\"execution(protected void com.sample.testapp.TraceTestServlet.doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse))\",\"execTime\":0,\"action\":\"ENTERING\",\"inputParams\":\"org.apache.catalina.connector.RequestFacade@6bc69a40 , org.apache.catalina.connector.ResponseFacade@3bb91707\",\"returnVals\":\"null\",\"requestId\":2015929,\"children\":[{\"methodName\":\"execution(public com.sample.testapp.SampleObject com.sample.testapp.HelperClass.doCallMethod(java.lang.String, int))\",\"execTime\":615,\"action\":\"ENTERING\",\"inputParams\":\"string , 12500\",\"returnVals\":\"com.sample.testapp.SampleObject@7cb9195\",\"requestId\":2015929,\"children\":[{\"methodName\":\"execution(public void com.sample.testapp.SampleObject.setIntegerVal(int))\",\"execTime\":0,\"action\":\"ENTERING\",\"inputParams\":\"225\",\"returnVals\":\"null\",\"requestId\":2015929,\"children\":[]},{\"methodName\":\"execution(public void com.sample.testapp.SampleObject.setList(java.util.List))\",\"execTime\":0,\"action\":\"ENTERING\",\"inputParams\":\"[str1]\",\"returnVals\":\"null\",\"requestId\":2015929,\"children\":[]},{\"methodName\":\"execution(public void com.sample.testapp.SampleObject.setMap(java.util.Map))\",\"execTime\":0,\"action\":\"ENTERING\",\"inputParams\":\"{key\u003d100}\",\"returnVals\":\"null\",\"requestId\":2015929,\"children\":[]},{\"methodName\":\"execution(public void com.sample.testapp.SampleObject.setStrvalue(java.lang.String))\",\"execTime\":0,\"action\":\"ENTERING\",\"inputParams\":\"StringValue\",\"returnVals\":\"null\",\"requestId\":2015929,\"children\":[]},{\"methodName\":\"execution(public java.lang.String com.sample.testapp.HelperClass.doCallMethod2(com.sample.testapp.SampleObject))\",\"execTime\":605,\"action\":\"ENTERING\",\"inputParams\":\"com.sample.testapp.SampleObject@7cb9195\",\"returnVals\":\"called-1\",\"requestId\":2015929,\"children\":[{\"methodName\":\"execution(public void com.sample.testapp.HelperClass.slowMethod())\",\"execTime\":600,\"action\":\"ENTERING\",\"inputParams\":\"\",\"returnVals\":\"null\",\"requestId\":2015929,\"children\":[]}]}]}]}]";
	response.setContentType("application/json");
	response.getWriter().write(jsonReturn.toString());
	
	
%>
<%!
void createJsontree(GenericTreeNode<TraceStatsBean> node,
		TraceStatsBean root) {
	// root = node.getData();
	for (int i = 0; i < node.getChildren().size(); i++) {
		TraceStatsBean childNode = node.getChildren().get(i).getData();
		root.addToChildren(childNode);
		if (node.getChildren().get(i).hasChildren()) {
			createJsontree(node.getChildren().get(i), childNode);
		}
	}

}
%>
