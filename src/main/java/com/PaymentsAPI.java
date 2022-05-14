package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Complain;

/**
 * Servlet implementation class PaymentsAPI
 */
@WebServlet("/PaymentsAPI")
public class PaymentsAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Payment comObj = new Payment();
    /**
     * Default constructor. 
     */
    public PaymentsAPI() {
    	super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		Payment comObj = new Payment();
		
		String output = "";
		output = comObj.readPayment();
		
		response.getWriter().append(output);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String output = comObj.insertPayment(
				request.getParameter("accountno"), 
				request.getParameter("nic"),
				request.getParameter("amount"), 
				request.getParameter("date"));
		response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map paras = getParasMap(request);
		String output = "";
		if (paras.get("idpayment") != null) {
			output = comObj.updatePayment(
					paras.get("idpayment").toString(),
					paras.get("accountno").toString(),
					paras.get("nic").toString(), 
					paras.get("amount").toString(), 
					paras.get("date").toString());
		}
		else {
			output = comObj.updatePayment(
					request.getParameter("idpayment"), 
					request.getParameter("accountno"), 
					request.getParameter("nic"), 
					request.getParameter("amount"), 
					request.getParameter("date"));
		}
		response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map paras = getParasMap(request);
		String output = "";
		if (paras.get("idpayment") != null) {
			output = comObj.deletePayment(paras.get("idpayment").toString());
		}
		else {
			output = comObj.deletePayment(request.getParameter("idpayment"));
		}
		System.out.println("ID: " + output);
		response.getWriter().write(output);
	}

	private static Map getParasMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
			scanner.close();
			String[] params = queryString.split("&");
			for (String param : params) {

				String[] p = param.split("=");
				map.put(p[0], p[1]);
			}
		} catch (Exception e) {
		}
		return map;
	}
}
