package com.expedia.assignment.dashboard;

import java.util.Scanner;

import com.expedia.assignment.dashboard.exception.FootballGameException;
import com.expedia.assignment.dashboard.service.CommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;


@SpringBootApplication
@RequiredArgsConstructor
public class DashboardApplication implements CommandLineRunner
{
	private final CommandService commandService;

	public static void main(String[] args)
	{
		SpringApplication.run(DashboardApplication.class, args);
	}

	@Override
	public void run(final String... strings) throws Exception
	{
		boolean canContinue = true;
		final Scanner scanner = new Scanner(System.in);
		while (canContinue)
		{
			System.out.print("Enter your command: ");
			try
			{
				String inputCommand = scanner.nextLine();
				if (!StringUtils.isEmpty(inputCommand))
				{
					commandService.executeCommand(inputCommand);
				}
			}
			catch (final FootballGameException exception)
			{
				System.out.println(exception.getMessage());
			}
		}
	}
}
