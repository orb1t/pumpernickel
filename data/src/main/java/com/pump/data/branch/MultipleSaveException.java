package com.pump.data.branch;

import java.util.ArrayList;
import java.util.List;

public class MultipleSaveException extends SaveException {
	private static final long serialVersionUID = 1L;
	
	protected List<SaveException> allProblems;
	
	public MultipleSaveException(@SuppressWarnings("rawtypes") Branch branch,
			List<SaveException> allProblems) {
		super(branch, createMessage(allProblems), allProblems.get(0));
		
		this.allProblems = new ArrayList<>(allProblems);
	}
	
	public List<SaveException> getSaveExceptions() {
		return new ArrayList<>(allProblems);
	}

	private static String createMessage(List<SaveException> allProblems) {
		StringBuilder sb = new StringBuilder();
		sb.append("Multiple problems were observed:\n");
		for(SaveException e : allProblems) {
			sb.append(e.getMessage()+"\n");
		}
		return sb.toString().trim();
	}

}
