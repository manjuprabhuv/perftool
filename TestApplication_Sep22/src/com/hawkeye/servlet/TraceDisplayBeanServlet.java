package com.hawkeye.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.hawkeye.beans.TraceStatsBean;
import com.hawkeye.db.DerbyDbUtils;
import com.hawkeye.utils.GenericTree;
import com.hawkeye.utils.GenericTreeNode;

public class TraceDisplayBeanServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

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
										&& list.get(j).getAction()
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
						str[h]=jsonData;
						//System.out.println(jsonData);
					}

				}
			}
		}
		// tree.buildWithDepth(GenericTreeTraversalOrderEnum.PRE_ORDER);
		// tree.getRoot().getChildren().toArray()
		
		String jsonReturn ="[";
		for (int i = 0; i < str.length; i++) {
			
			jsonReturn = jsonReturn +str[i];

			if(i!=str.length-1){
				jsonReturn = jsonReturn+",";
			}
		}
		jsonReturn = jsonReturn +"]";
		System.out.println(jsonReturn);
		// req.setAttribute("TracestatsList", list);
		RequestDispatcher rd = req.getRequestDispatcher("/TraceDisplayUI.jsp");
		rd.forward(req, res);
	}

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
}