package com.expedia.assignment.dashboard.domain;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class Game
{
	private Team homeTeam;
	private Team awayTeam;
	@Builder.Default
	private GameStatus status = GameStatus.NOT_STARTED;

	@Override
	public String toString()
	{
		final StringBuilder resultString = new StringBuilder();
		resultString.append(getHomeTeam().toString());
		resultString.append(" ").append("vs.").append(" ");
		resultString.append(getAwayTeam().toString());
		return resultString.toString();
	}
}
