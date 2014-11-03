package com.sakisakisoftware.emmanutt;

import java.util.ArrayList;
import java.util.Random;

public class ProblemGenerator {

	public int evaluate(ArrayList<Integer> listVals, ArrayList<Character> listOps)
	{
		int[] vals = new int[listVals.size()];
		char[] ops = new char[listOps.size()];
		for (int i = 0; i < vals.length; i++)
			vals[i] = listVals.get(i);
		for (int i = 0; i < ops.length; i++)
			ops[i] = listOps.get(i);
		return evaluate(vals, ops);
	}
	
	public int evaluate(int[] vals, char[] ops)
	{
		if (vals.length != ops.length + 1)
		{
			throw new IllegalArgumentException("number of elements is invalid");
		}
		
		int val = vals[0];

		ArrayList<Integer> listVals = new ArrayList<Integer>();
		ArrayList<Character> listOps = new ArrayList<Character>();
		for (int i = 0; i < ops.length; i++)
		{
			char op = ops[i];
			int next = vals[i + 1];
			if (op == '+' || op == '-')
			{
				listVals.add(val);
				listOps.add(op);
				val = next;
			}
			else if (op == '*')
			{
				val *= next;
			}
			else if (op == '/')
			{
				val /= next;
			}
			else
			{
				throw new IllegalArgumentException("invalid operator");
			}
		}
		listVals.add(val);

		val = listVals.get(0);
		for (int i = 0; i < listOps.size(); i++)
		{
			char op = listOps.get(i);
			int next = listVals.get(i + 1);
			if (op == '+')
			{
				val += next;
			}
			else if (op == '-')
			{
				val -= next;
			}
		}

		System.out.print(vals[0]);
		for (int i = 0; i < ops.length; i++)
		{
			System.out.print(ops[i]);
			System.out.print(vals[i + 1]);
		}
		System.out.print(" = ");
		System.out.println(val);

		return val;
	}
	
	void testEvaluate()
	{
		int[] vals = new int[4];
		vals[0] = 1;
		vals[1] = 2;
		vals[2] = 3;
		vals[3] = 4;
		
		char[] ops = new char[3];
		ops[0] = '+';
		ops[1] = '*';
		ops[2] = '-';
		
		evaluate(vals, ops);
	}

	/*
	static int getRandom(int max)
	{
		double fMax = max + 0.99999;
		int ret = (int)(Math.random() * fMax);
		ret = Math.min(ret, max);
		return ret;
	}
	*/
	
	int getNextVal(int val)
	{
		if (val > 1)
			return val - 1;
		else
			return 9;
	}
	
	char getNextOp(char op, boolean easyMode)
	{
		if (easyMode)
		{
			if (op == '+')
				return '-';
		}
		else
		{
			if (op == '+')
				return '-';
			if (op == '-')
				return '*';
			if (op == '*')
				return '/';
			if (op == '/')
				return '+';
		}
		return '+';
	}

	boolean checkOk(int val, char op, int last, ArrayList<Integer> listVals, ArrayList<Character> listOps)
	{
		if (op == '/')
		{
			if ((last % val) != 0)
			{
				return false;
			}
		}
		
		int[] vals = new int[listVals.size() + 1];
		char[] ops = new char[listOps.size() + 1];
		for (int i = 0; i < listVals.size(); i++)
		{
			vals[i] = listVals.get(i);
		}
		vals[listVals.size()] = val;
		
		for (int i = 0; i < listOps.size(); i++)
		{
			ops[i] = listOps.get(i);
		}
		ops[listOps.size()] = op;
		
		int ret = evaluate(vals, ops);
		if (ret < 0)
			return false;
		
		return true;
	}

	// pick a number and save it as last number

	// pick an operator
	// pick a number
	// 	if last operator is /, this number must be CD of last number
	//  if last operator is x or /, last number = last number x num or /num
	//  if last operator is + or -, last number = num
	// evaluate the result, if it's < 0, try to pick other number,
	// if no number is found, change the operator
	public int generateProblem(boolean easyMode, int level, ArrayList<Integer> listVals, ArrayList<Character> listOps)
	{
		Random r = new Random();
		
		char[] ops = new char[4];
		ops[0] = '+';
		ops[1] = '-';
		ops[2] = '*';
		ops[3] = '/';

		int i0 = r.nextInt(9) + 1; // 1-9
		listVals.add(i0);
		int last = i0;
		for (int i = 0; i < level; i++)
		{
			int opNum = r.nextInt(easyMode ? 2 : 4); // 0-3
			char op = ops[opNum];
			int val = r.nextInt(9) + 1;
			boolean ok = false;
			for (int j = 0; j < 4; j++)
			{
				for (int k = 1; k <= 9; k++)
				{
					ok = checkOk(val, op, last, listVals, listOps);
					if (ok)
						break;
					val = getNextVal(val);
				}
				if (ok)
					break;
				op = getNextOp(op, easyMode);
			}
			
			if (op == '/')
				last /= val;
			else if (op == '*')
				last *= val;
			else
				last = val;
			
			listOps.add(op);
			listVals.add(val);
		}
		
		return evaluate(listVals, listOps);
	}
	
}
