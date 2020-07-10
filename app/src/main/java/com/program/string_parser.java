package com.program;

import java.util.StringTokenizer;

import android.content.Intent;


public class string_parser
{

	Message_generator message_command;
	/*
	public string_parser() {
		message_command = new Message_generator();
	}
	*/
	public void select_string(String message)
	{
		StringTokenizer stringtokenizertest = new StringTokenizer(message, "\\p");
		String mes = "";
		while (stringtokenizertest.hasMoreElements())
		{
			mes = stringtokenizertest.nextToken();
			if ((mes.charAt(0) == ':') && ((mes.charAt(3) == 'X') || (mes.charAt(3) == 'x')) && ((mes.charAt(4) == 'X') || (mes.charAt(4) == 'x')))
			{
				if (Main_activity.panel1 == 1) {Main_activity.sendstring(":" + mes.charAt(1) + mes.charAt(2) + "01\r"); wait_period(50);}
				if (Main_activity.panel2 == 1) {Main_activity.sendstring(":" + mes.charAt(1) + mes.charAt(2) + "02\r"); wait_period(50);}
				if (Main_activity.panel3 == 1) {Main_activity.sendstring(":" + mes.charAt(1) + mes.charAt(2) + "03\r"); wait_period(50);}
				if (Main_activity.panel4 == 1) {Main_activity.sendstring(":" + mes.charAt(1) + mes.charAt(2) + "04\r"); wait_period(50);}
				if (Main_activity.panel5 == 1) {Main_activity.sendstring(":" + mes.charAt(1) + mes.charAt(2) + "05\r"); wait_period(50);}
				if (Main_activity.panel6 == 1) {Main_activity.sendstring(":" + mes.charAt(1) + mes.charAt(2) + "06\r"); wait_period(50);}
				if (Main_activity.panel7 == 1) {Main_activity.sendstring(":" + mes.charAt(1) + mes.charAt(2) + "07\r"); wait_period(50);}
				if (Main_activity.panel8 == 1) {Main_activity.sendstring(":" + mes.charAt(1) + mes.charAt(2) + "08\r"); wait_period(50);}
				if (Main_activity.panel9 == 1) {Main_activity.sendstring(":" + mes.charAt(1) + mes.charAt(2) + "09\r"); wait_period(50);}
				if (Main_activity.panel10 == 1) {Main_activity.sendstring(":" + mes.charAt(1) + mes.charAt(2) + "10\r"); wait_period(50);}
			}

			else if ((mes.charAt(0) == '*') && ((mes.charAt(3) == 'X') || (mes.charAt(3) == 'x')) && ((mes.charAt(4) == 'X') || (mes.charAt(4) == 'x')))
			{
				if (Main_activity.hp1 == 1) {Main_activity.sendstring("*" + mes.charAt(1) + mes.charAt(2) + "01\r"); wait_period(50);}
				if (Main_activity.hp2 == 1) {Main_activity.sendstring("*" + mes.charAt(1) + mes.charAt(2) + "02\r"); wait_period(50);}
				if (Main_activity.hp3 == 1) {Main_activity.sendstring("*" + mes.charAt(1) + mes.charAt(2) + "03\r"); wait_period(50);}

			}
			else
				Main_activity.sendstring(mes + '\r');
			wait_period(100);
		}
	}
	
	void wait_period(int testing)
	{
		try {
			Thread.sleep(testing);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}