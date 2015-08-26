package com.suru.fts.voter;



public interface ToggleVoter<T> {

	public boolean vote(final String id, final T strategy);
}
