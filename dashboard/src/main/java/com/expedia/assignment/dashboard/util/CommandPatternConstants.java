package com.expedia.assignment.dashboard.util;

/**
 * Patterns representing the valid game commands
 */
public class CommandPatternConstants
{
	public static final String START_GAME_COMMAND_PATTERN = "^Start: '(?<homeTeam>.+)' vs. '(?<awayTeam>.+)'$";
	public static final String UPDATE_SCORE_COMMAND_PATTERN = "^(?<scoreMinute>\\d+) '(?<teamName>.+)' (?<playerName>.+)$";
	public static final String PRINT_SCORE_COMMAND_PATTERN = "print";
	public static final String END_GAME_COMMAND_PATTERN = "End";

}
