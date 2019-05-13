package com.example.demo.service.impl;

import com.example.demo.converter.UserConverter;
import com.example.demo.dto.MyEntry;
import com.example.demo.dto.TrainingForecastTableData;
import com.example.demo.dto.UserDto;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.utils.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ashish on 13/5/17.
 */
@Service
public class UserServiceimpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDto getUserById(Integer userId) {
		return UserConverter.entityToDto(userRepository.getOne(userId));
	}

	@Override
	public void saveUser(UserDto userDto) {
		userRepository.save(UserConverter.dtoToEntity(userDto));
	}

	@Override
	public List<UserDto> getAllUsers() {
		return userRepository.findAll().stream().map(UserConverter::entityToDto).collect(Collectors.toList());
	}

	@Override
	public TrainingForecastTableData getTrainingForecast() {
		TrainingForecastTableData result = new TrainingForecastTableData();
		
		Random rand = new Random();

		List<MyEntry<Long, String>> columns = new ArrayList<>();
		for (int i = 1; i <= 5+ rand.nextInt(6); i++) {
			columns.add(new MyEntry<Long, String>((long) i, "D" + i));
		}
		result.setColumns(columns);

		List<List> rows = new ArrayList<>();
		for (int i = 1; i <= 10 + rand.nextInt(11); i ++) {
			List row = new ArrayList<>();
			row.add(new MyEntry<Long, String>((long) i, "Sp" + i));
			rows.add(row);
		}
		result.setRows(rows);

		Calendar c = Calendar.getInstance();
		int currentMonth = c.get(Calendar.MONTH);
		for (String month : getMonths(2, 8)) {
			// in repository, we are including "from" and excluding "to" dates.
			Date from = Util.parseMonthYear(month);
			c.setTime(from);
			int entityMonth = c.get(Calendar.MONTH);
			c.add(Calendar.MONTH, 1);// 00:00:00.000 next month
			Date to = c.getTime();

			MyEntry<String, Boolean> monthEntry = new MyEntry<String, Boolean>(month, currentMonth == entityMonth);
			result.getMonths().add(monthEntry);

			for (List row : rows) {
				for (MyEntry<Long, String> column : columns) {
					Integer value = rand.nextInt(100);
					String color = "";
					if (value % 7 == 0) {
						color = "Red";
					} else if (value % 5 == 0) {
						color = "Green";
					}

					row.add(new MyEntry<Integer, String>(value, color));
				}
			}
		}
		return result;
	}

	private List<String> getMonths(int monthsBefore, int monthsAfter) {
		List<String> result = new ArrayList<>(monthsBefore + monthsAfter);

		Calendar c = Calendar.getInstance();
		c.set(Calendar.DATE, 1);
		c = Util.setTimeToBeginningOfDay(c);
		c.add(Calendar.MONTH, monthsBefore * -1);

		for (int n = 0; n < monthsBefore + monthsAfter; n++) {
			Date from = c.getTime();// 00:00:00.000 current month
			String monthYearString = Util.formatMonthYear(from);
			result.add(monthYearString);
			c.add(Calendar.MONTH, 1);// 00:00:00.000 next month
		}
		return result;
	}
}
