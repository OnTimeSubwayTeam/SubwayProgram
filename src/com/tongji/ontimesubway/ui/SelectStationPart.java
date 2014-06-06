package com.tongji.ontimesubway.ui;

import com.tongji.ontimesubway.R;
import com.tongji.ontimesubway.R.layout;
import com.tongji.ontimesubway.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SelectStationPart extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selectstationpart);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_station_part, menu);
		return true;
	}

}
