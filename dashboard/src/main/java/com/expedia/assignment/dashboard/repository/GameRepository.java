package com.expedia.assignment.dashboard.repository;

import java.util.Optional;

import com.expedia.assignment.dashboard.domain.Game;
import lombok.Setter;
import org.springframework.stereotype.Repository;


/**
 * Un-implimented repository class, for performing CRUD operations on game
 */
@Repository
public class GameRepository
{
	//I would have preferred to use JPARepository to fetch the running game, but since the expectations of this project was to
	//keep simple. So, i am keeping the reference of running game here itself, instead of updating/fetching it in the database
	@Setter
	private Game runningGame;

	public Optional<Game> getRunningGame()
	{
		return Optional.ofNullable(runningGame);
	}

	public void endRunningGame()
	{
		this.runningGame = null;
	}
}
