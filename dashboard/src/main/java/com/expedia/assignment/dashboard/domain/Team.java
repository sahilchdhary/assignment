package com.expedia.assignment.dashboard.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Team
{
	private String name;
	@Builder.Default
	private Player[] players = new Player[12];
	@Builder.Default
	private List<Goal> goals = new ArrayList<>();

	@Override
	public String toString()
	{
		final StringBuilder resultString = new StringBuilder();
		resultString.append(name).append(" ").append(goals.size());
		if (!getGoals().isEmpty())
		{
			resultString.append(" ").append("(");
			goals.forEach(goal -> resultString.append(goal.toString()).append(" "));
			resultString.deleteCharAt(resultString.length() - 1);
			resultString.append(")");
		}
		return resultString.toString();
	}
}
