package com.expedia.assignment.dashboard.domain;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Goal
{
	private Integer scoreMinute;
	private Player player;

	@Override
	public String toString()
	{
		return new StringBuilder().append(player.getName()).append(" ").append(scoreMinute).append("'").toString();
	}
}
