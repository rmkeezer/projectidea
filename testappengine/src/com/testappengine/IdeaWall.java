package com.testappengine;

import com.testappengine.deviceinfoendpoint.Deviceinfoendpoint;
import com.testappengine.deviceinfoendpoint.model.CollectionResponseDeviceInfo;
import com.testappengine.deviceinfoendpoint.model.DeviceInfo;
import com.testappengine.ideaendpoint.Ideaendpoint;
import com.testappengine.ideaendpoint.model.CollectionResponseIdea;
import com.testappengine.ideaendpoint.model.Idea;
import com.testappengine.userendpoint.Userendpoint;
import com.testappengine.userendpoint.model.CollectionResponseUser;
import com.testappengine.userendpoint.model.User;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.DateTime;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class IdeaWall extends Activity implements OnClickListener {

	private View notLoggedIn, loggedIn;
	private AnimatedExpandableListView cardsList;
	private CardsAdapter ideaList;
	private static ArrayList<Idea> ideaArray, newerSortedArray, olderSortedArray,
			topSortedArray, bottomSortedArray, hotSortedArray, nothotSortedArray, 
			catSortedArray;
	private static int currentArray;
	private Button login,logOut,register,postIdea,refresh;
	private Dialog logDialog,regDialog,postDialog,replyDialog;
	private EditText username,password,email,ideaText,replyText;
	private TextView promTxt;
	private Spinner ideaCategories, ideaCatFilter, ideaTopFilter;
	public static User currentUser;
	private UserData currentUserData;
	private String currentDeviceID;
	private DeviceInfo currentDevice;
	private DeviceData currentDeviceData;
	private boolean updating,showReplyDialog,isLoggedIn,hasLoggedInOnce,postReply;
	private TabHost tabHost;
	private AdView adView;
	private static final String AD_UNIT_ID = "5680242733";

	LayoutInflater layoutInflater;
	View popupView;
	PopupWindow popupWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		notLoggedIn = getLayoutInflater().inflate(R.layout.ideawall, null);
		loggedIn = getLayoutInflater().inflate(R.layout.ideawallloggedin, null);
		setContentView(notLoggedIn);
		
		currentArray = 0;
		isLoggedIn = false;
		hasLoggedInOnce = false;
		postReply = false;
		currentDeviceID = getIntent().getExtras().getString("deviceID");
		showReplyDialog = false;
		currentUserData = new UserData();
		currentDeviceData = new DeviceData();
		ideaArray = new ArrayList<Idea>();
		newerSortedArray = new ArrayList<Idea>();
		topSortedArray = new ArrayList<Idea>();
		hotSortedArray = new ArrayList<Idea>();
		catSortedArray = new ArrayList<Idea>();
		setupList(isLoggedIn);

		new RetrieveDevice().execute();
		new RetrieveIdeas().execute();
		
		// Create an ad.
	    adView = new AdView(this);
	    adView.setAdSize(AdSize.BANNER);
	    adView.setAdUnitId(AD_UNIT_ID);
	    
	    // Add the AdView to the view hierarchy. The view will have no size
	    // until the ad is loaded.
	    LinearLayout layout = (LinearLayout) findViewById(R.id.adbanner);
	    layout.addView(adView);
	    
	    // Create an ad request. Check logcat output for the hashed device ID to
	    // get test ads on a physical device.
	    final TelephonyManager tm =(TelephonyManager)getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
	    String deviceid = tm.getDeviceId();
	    AdRequest adRequest = new AdRequest.Builder()
	        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
	        .addTestDevice(deviceid)
	        .build();

	    // Start loading the ad in the background.
	    adView.loadAd(adRequest);
	}
	@Override
	public void onResume() {
	    super.onResume();
	    if (adView != null) {
	      adView.resume();
	    }
	  }
	  @Override
	public void onPause() {
	    if (adView != null) {
	      adView.pause();
	    }
	    super.onPause();
	  }
	  @Override
	public void onDestroy() {
	    // Destroy the AdView.
	    if (adView != null) {
	      adView.destroy();
	    }
	    super.onDestroy();
	  }
	private void setupList(boolean loggedIn) {
			setupTabs();
			if (loggedIn) {
				cardsList = (AnimatedExpandableListView) findViewById(R.id.cards_list_loggedin);
				postIdea = (Button) findViewById(R.id.bPostIdea);
				postIdea.setOnClickListener(IdeaWall.this);
				refresh = (Button) findViewById(R.id.bRefresh);
				refresh.setOnClickListener(IdeaWall.this);
				logOut = (Button) findViewById(R.id.bLogOut);
				logOut.setOnClickListener(IdeaWall.this);
				promTxt = (TextView) findViewById(R.id.tvPromTxt);
				hasLoggedInOnce = true;
			} else {
				cardsList = (AnimatedExpandableListView) findViewById(R.id.cards_list);
				postIdea = (Button) findViewById(R.id.bPostIdea);
				postIdea.setOnClickListener(this);
				refresh = (Button) findViewById(R.id.bRefresh);
				refresh.setOnClickListener(this);
				login = (Button) findViewById(R.id.bLogin);
				login.setOnClickListener(this);
				register = (Button) findViewById(R.id.bRegister);
				register.setOnClickListener(this);
			}
			
			ideaList = createAdapter(ideaArray);
			cardsList.setAdapter(ideaList);
			cardsList.setOnItemClickListener(new ListItemClickListener());
			cardsList.setOnScrollListener(new AbsListView.OnScrollListener() {

				@Override
				public void onScrollStateChanged(AbsListView view,
						int scrollState) {
					// TODO Auto-generated method stub
					switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                                         // when list scrolling stops
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        break;
                    }
					
				}

				@Override
				public void onScroll(AbsListView view, int firstVisibleItem,
						int visibleItemCount, int totalItemCount) {
					// TODO Auto-generated method stub
				}
				
			});
	}
	private void setupList2(boolean loggedIn) {
		// Used for reinitializing Category tab layout
		if (loggedIn) {
			cardsList = (AnimatedExpandableListView) findViewById(R.id.cards_list_loggedin2);
		} else {
			cardsList = (AnimatedExpandableListView) findViewById(R.id.cards_list2);
		}
	}
	private void setupList3(boolean loggedIn) {
		// Used for reinitializing Top tab layout
		if (loggedIn) {
			cardsList = (AnimatedExpandableListView) findViewById(R.id.cards_list_loggedin3);
		} else {
			cardsList = (AnimatedExpandableListView) findViewById(R.id.cards_list3);
		}
	}
	private void setupTabs() {
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup();
		if(!hasLoggedInOnce) {
			TabSpec newTab = tabHost.newTabSpec("New Tab");
			TabSpec topTab = tabHost.newTabSpec("Top tab");
			TabSpec hotTab = tabHost.newTabSpec("H`ot tab");
			TabSpec catTab = tabHost.newTabSpec("Category tab");
			newTab.setIndicator("New");
			newTab.setContent(R.id.tab1stuff);
			topTab.setIndicator("Top");
			topTab.setContent(R.id.tab3stuff);
			hotTab.setIndicator("Hot");
			hotTab.setContent(R.id.tab1stuff);
			catTab.setIndicator("Category");
			catTab.setContent(R.id.tab2stuff);
			tabHost.addTab(newTab);
			tabHost.addTab(topTab);
			tabHost.addTab(hotTab);
			tabHost.addTab(catTab);
			tabHost.getTabWidget().getChildAt(0).getLayoutParams().width = 30;
			tabHost.getTabWidget().getChildAt(1).getLayoutParams().width = 30;
			tabHost.getTabWidget().getChildAt(2).getLayoutParams().width = 30;
			tabHost.getTabWidget().getChildAt(3).getLayoutParams().width = 80;
		}
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				currentArray = getTabHost().getCurrentTab();
				if(currentArray == 1) {
					setupList3(isLoggedIn);
					ideaTopFilter = (Spinner) findViewById(R.id.sTopFilter);
					ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
							IdeaWall.this, R.array.topfilters, android.R.layout.simple_spinner_item);
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					ideaTopFilter.setAdapter(adapter);
				} else if(currentArray == 3) {
					setupList2(isLoggedIn);
					ideaCatFilter = (Spinner) findViewById(R.id.sCatFilter);
					ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
							IdeaWall.this, R.array.categories, android.R.layout.simple_spinner_item);
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					ideaCatFilter.setAdapter(adapter);
				}
				new RetrieveIdeas().execute();

			}
		});
	}

	private CardsAdapter createAdapter(ArrayList<Idea> ideaList) {
		return new CardsAdapter(IdeaWall.this, ideaList,
				new ListItemButtonClickListener(), currentUserData,
				currentDeviceData);
	}
	private final class ListItemButtonClickListener implements OnClickListener {
	  @Override
	  public void onClick(View v) {
	    for (int i = cardsList.getFirstVisiblePosition(); i <= cardsList.getLastVisiblePosition(); i++) {
		  if (updating /*|| i > ideaList.getGroupCount()*/)
		    return;
		  long packedPosition = cardsList.getExpandableListPosition(i);
		  int gpSort = ExpandableListView.getPackedPositionGroup(packedPosition);
		  if (gpSort == -1)
			  return;
		  int gp = sortToRealPos(gpSort);
		  int cp = ExpandableListView.getPackedPositionChild(packedPosition);
		  View newView = cardsList.getChildAt(i - cardsList.getFirstVisiblePosition());
		  ImageButton tempImgBut = (ImageButton) newView.findViewById(R.id.ibCheckmark);
		  // The nested if statements look horrible but this way is faster
		  if (v == tempImgBut && (!ideaChecked(gp) && !ideaCrossed(gp))) {
		    tempImgBut.setImageResource(R.drawable.checkmark);
			updating = true;
			if (currentUser != null) {
			  new UpdateUser().execute(gp, 1);
			  new UpdateDevice().execute(gp, 1);
			} else {
			  new UpdateDevice().execute(gp, 1);
			}
			new UpdateIdea().execute(gp, 1);
		  } else {
			tempImgBut = (ImageButton) newView.findViewById(R.id.ibCrossmark);
			if (v == tempImgBut && (!ideaCrossed(gp) && !ideaChecked(gp))) {
			  tempImgBut.setImageResource(R.drawable.cross);
			  updating = true;
			  if (currentUser != null) {
			    new UpdateUser().execute(gp, 2);
				new UpdateDevice().execute(gp, 2);
			  } else {
				new UpdateDevice().execute(gp, 2);
			  }
			  new UpdateIdea().execute(gp, 2);
			} else {
			  Button tempButton = (Button) newView.findViewById(R.id.bPromotionCard);
			  if (v == tempButton) {
				if (currentUser != null && currentUser.getPromotions() > 0) {
				  tempButton.setBackgroundColor(Color.rgb(100, 180, 255));
				  updating = true;
				  new UpdateUser().execute(1, 9);
				  new UpdateIdea().execute(gp, 3);
				  currentUser.setPromotions(currentUser.getPromotions()-1);
				  Toast.makeText(IdeaWall.this, "Promotions left: " + currentUser.getPromotions(), Toast.LENGTH_SHORT).show();
				} else {
				  Toast.makeText(IdeaWall.this, "No Promotions availabe", Toast.LENGTH_SHORT).show();
				}
			  } else {
				tempButton = (Button) newView.findViewById(R.id.bJunkButton);
				if (v == tempButton && !ideaJunked(gp)) {
				  tempButton.setBackgroundColor(Color.rgb(168, 137, 88));
				  updating = true;
			      if (currentUser != null) {
					new UpdateUser().execute(gp, 4);
					new UpdateDevice().execute(gp, 4);
				  } else {
					new UpdateDevice().execute(gp, 4);
				  }
				  new UpdateIdea().execute(gp, 4);
				} else {
				  tempButton = (Button) newView.findViewById(R.id.bViewComments);
				  if (v == tempButton && ideaArray.get(gp).getComments() != null) {
					if (cardsList.isGroupExpanded(gpSort)) {
					  cardsList.collapseGroupWithAnimation(gpSort);
					  } else {
						cardsList.expandGroupWithAnimation(gpSort);
					  }
					} else {
					  tempButton = (Button) newView.findViewById(R.id.bReply);
					  if (v == tempButton) {
					    showReplyDialog = true;
						new UpdateIdea().execute(gp, 5);
						new RetrieveDevice().execute();
					  } else {
						tempImgBut = (ImageButton) newView.findViewById(R.id.ibComCheckmark);
						if (v == tempImgBut && (!commentChecked(gp, cp) && !commentCrossed(gp, cp))) {
						  updating = true;
						  if (currentUser != null) {
						    new UpdateUser().execute(gp, 5, cp);
							new UpdateDevice().execute(gp,5, cp);
						  } else {
						    new UpdateDevice().execute(gp, 5, cp);
						  }
						  new UpdateIdea().execute(gp, 6, cp);
						} else {
						  tempImgBut = (ImageButton) newView.findViewById(R.id.ibComCrossmark);
						  if (v == tempImgBut && (!commentChecked(gp, cp) && !commentCrossed(gp, cp))) {
							updating = true;
							if (currentUser != null) {
							  new UpdateUser().execute(gp, 6, cp);
							  new UpdateDevice().execute(gp, 6, cp);
							} else {
							  new UpdateDevice().execute(gp, 6, cp);
							}
							new UpdateIdea().execute(gp, 7, cp);
						  } else {
							tempButton = (Button) newView.findViewById(R.id.bComPromotionCard);
							if (v == tempButton) {
							  updating = true;
							  if (currentUser != null) {
								new UpdateUser().execute(gp, 7, cp);
								new UpdateDevice().execute(gp, 7, cp);
							  } else {
								new UpdateDevice().execute(gp, 7, cp);
							  }
							  new UpdateIdea().execute(gp, 8, cp);
						  } else {
							tempButton = (Button) newView.findViewById(R.id.bComJunkButton);
							if (v == tempButton) {
							  updating = true;
							  if (currentUser != null) {
								new UpdateUser().execute(gp, 8, cp);
								new UpdateDevice().execute(gp, 8, cp);
							  } else {
								new UpdateDevice().execute(gp, 8, cp);
							  }
							  new UpdateIdea().execute(gp, 9, cp);
							}
						  }
					    }
					  }
					}
				  }
				}
			  }
		    }
		  }
	    }
	  }
	}
	private final class ListItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Toast.makeText(IdeaWall.this, "Clicked on List Item " + position,
					Toast.LENGTH_SHORT).show();
		}
	}
	
	private class UpdateDevice extends AsyncTask<Integer, Void, Void> {
		@Override
		protected Void doInBackground(Integer... params) {

			Deviceinfoendpoint.Builder builder = new Deviceinfoendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);

			builder = CloudEndpointUtils.updateBuilder(builder);

			Deviceinfoendpoint endpoint = builder.build();

			// If the variables are not set to -1, they are never initialized
			if (params[1] == 1) {
				currentDevice.setUpvotedIdeasToAdd(params[0]);
				currentDevice.setDownvotedIdeasToAdd(-1);
				currentDevice.setPromotedIdeasToAdd(-1);
				currentDevice.setJunkedIdeasToAdd(-1);
				currentDevice.setComUpToAdd(-1);
				currentDevice.setComUpToAdd2(-1);
				currentDevice.setComDownToAdd(-1);
				currentDevice.setComDownToAdd2(-1);
				currentDevice.setComPromToAdd(-1);
				currentDevice.setComJunkToAdd(-1);
				currentDevice.setComJunkToAdd2(-1);
			} else if (params[1] == 2) {
				currentDevice.setDownvotedIdeasToAdd(params[0]);
				currentDevice.setUpvotedIdeasToAdd(-1);
				currentDevice.setPromotedIdeasToAdd(-1);
				currentDevice.setJunkedIdeasToAdd(-1);
				currentDevice.setComUpToAdd(-1);
				currentDevice.setComUpToAdd2(-1);
				currentDevice.setComDownToAdd(-1);
				currentDevice.setComDownToAdd2(-1);
				currentDevice.setComPromToAdd(-1);
				currentDevice.setComJunkToAdd(-1);
				currentDevice.setComJunkToAdd2(-1);
			} else if (params[1] == 3) {
				currentDevice.setPromotedIdeasToAdd(params[0]);
				currentDevice.setUpvotedIdeasToAdd(-1);
				currentDevice.setDownvotedIdeasToAdd(-1);
				currentDevice.setJunkedIdeasToAdd(-1);
				currentDevice.setComUpToAdd(-1);
				currentDevice.setComUpToAdd2(-1);
				currentDevice.setComDownToAdd(-1);
				currentDevice.setComDownToAdd2(-1);
				currentDevice.setComPromToAdd(-1);
				currentDevice.setComJunkToAdd(-1);
				currentDevice.setComJunkToAdd2(-1);
			} else if (params[1] == 4) {
				currentDevice.setJunkedIdeasToAdd(params[0]);
				currentDevice.setComUpToAdd2(-1);
				currentDevice.setUpvotedIdeasToAdd(-1);
				currentDevice.setDownvotedIdeasToAdd(-1);
				currentDevice.setPromotedIdeasToAdd(-1);
				currentDevice.setComUpToAdd(-1);
				currentDevice.setComDownToAdd(-1);
				currentDevice.setComDownToAdd2(-1);
				currentDevice.setComPromToAdd(-1);
				currentDevice.setComJunkToAdd(-1);
				currentDevice.setComJunkToAdd2(-1);
			} else if (params[1] == 5) {
				currentDevice.setComUpToAdd(params[0]);
				currentDevice.setComUpToAdd2(params[2]);
				currentDevice.setUpvotedIdeasToAdd(-1);
				currentDevice.setDownvotedIdeasToAdd(-1);
				currentDevice.setPromotedIdeasToAdd(-1);
				currentDevice.setJunkedIdeasToAdd(-1);
				currentDevice.setComDownToAdd(-1);
				currentDevice.setComDownToAdd2(-1);
				currentDevice.setComPromToAdd(-1);
				currentDevice.setComJunkToAdd(-1);
				currentDevice.setComJunkToAdd2(-1);
			} else if (params[1] == 6) {
				currentDevice.setComDownToAdd(params[0]);
				currentDevice.setComDownToAdd2(params[2]);
				currentDevice.setUpvotedIdeasToAdd(-1);
				currentDevice.setDownvotedIdeasToAdd(-1);
				currentDevice.setPromotedIdeasToAdd(-1);
				currentDevice.setJunkedIdeasToAdd(-1);
				currentDevice.setComUpToAdd(-1);
				currentDevice.setComUpToAdd2(-1);
				currentDevice.setComPromToAdd(-1);
				currentDevice.setComJunkToAdd(-1);
				currentDevice.setComJunkToAdd2(-1);
			} else if (params[1] == 7) {
				currentDevice.setComPromToAdd(params[2]);
				currentDevice.setUpvotedIdeasToAdd(-1);
				currentDevice.setDownvotedIdeasToAdd(-1);
				currentDevice.setPromotedIdeasToAdd(-1);
				currentDevice.setJunkedIdeasToAdd(-1);
				currentDevice.setComUpToAdd(-1);
				currentDevice.setComUpToAdd2(-1);
				currentDevice.setComDownToAdd(-1);
				currentDevice.setComDownToAdd2(-1);
				currentDevice.setComJunkToAdd(-1);
				currentDevice.setComJunkToAdd2(-1);
			} else if (params[1] == 8) {
				currentDevice.setComJunkToAdd(params[0]);
				currentDevice.setComJunkToAdd2(params[2]);
				currentDevice.setUpvotedIdeasToAdd(-1);
				currentDevice.setDownvotedIdeasToAdd(-1);
				currentDevice.setPromotedIdeasToAdd(-1);
				currentDevice.setJunkedIdeasToAdd(-1);
				currentDevice.setComUpToAdd(-1);
				currentDevice.setComUpToAdd2(-1);
				currentDevice.setComDownToAdd(-1);
				currentDevice.setComDownToAdd2(-1);
				currentDevice.setComPromToAdd(-1);
			}

			try {
				endpoint.updateDeviceInfo(currentDevice).execute();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			new RetrieveDevice().execute();

			return null;
		}
	}
	private class UpdateUser extends AsyncTask<Integer, Void, Void> {
		@Override
		protected Void doInBackground(Integer... params) {

			Userendpoint.Builder builder = new Userendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);

			builder = CloudEndpointUtils.updateBuilder(builder);

			Userendpoint endpoint = builder.build();

			// If the variables are not set to -1, they are never initialized
			if (params[1] == 1) {
				currentUser.setUpvotedIdeasToAdd(params[0]);
				currentUser.setDownvotedIdeasToAdd(-1);
				currentUser.setJunkedIdeasToAdd(-1);
				currentUser.setComUpToAdd(-1);
				currentUser.setComUpToAdd2(-1);
				currentUser.setComDownToAdd(-1);
				currentUser.setComDownToAdd2(-1);
				currentUser.setComPromToAdd(-1);
				currentUser.setComJunkToAdd(-1);
				currentUser.setComJunkToAdd2(-1);
				currentUser.setPromotionsToAdd(-1);
			} else if (params[1] == 2) {
				currentUser.setDownvotedIdeasToAdd(params[0]);
				currentUser.setUpvotedIdeasToAdd(-1);
				currentUser.setJunkedIdeasToAdd(-1);
				currentUser.setComUpToAdd(-1);
				currentUser.setComUpToAdd2(-1);
				currentUser.setComDownToAdd(-1);
				currentUser.setComDownToAdd2(-1);
				currentUser.setComPromToAdd(-1);
				currentUser.setComJunkToAdd(-1);
				currentUser.setComJunkToAdd2(-1);
				currentUser.setPromotionsToAdd(-1);
			} else if (params[1] == 3) {
				currentUser.setPromotions(currentUser.getPromotions() - params[0]);
				currentUser.setUpvotedIdeasToAdd(-1);
				currentUser.setDownvotedIdeasToAdd(-1);
				currentUser.setJunkedIdeasToAdd(-1);
				currentUser.setComUpToAdd(-1);
				currentUser.setComUpToAdd2(-1);
				currentUser.setComDownToAdd(-1);
				currentUser.setComDownToAdd2(-1);
				currentUser.setComPromToAdd(-1);
				currentUser.setComJunkToAdd(-1);
				currentUser.setComJunkToAdd2(-1);
				currentUser.setPromotionsToAdd(-1);
			} else if (params[1] == 4) {
				currentUser.setJunkedIdeasToAdd(params[0]);
				currentUser.setUpvotedIdeasToAdd(-1);
				currentUser.setDownvotedIdeasToAdd(-1);
				currentUser.setComUpToAdd(-1);
				currentUser.setComUpToAdd2(-1);
				currentUser.setComDownToAdd(-1);
				currentUser.setComDownToAdd2(-1);
				currentUser.setComPromToAdd(-1);
				currentUser.setComJunkToAdd(-1);
				currentUser.setComJunkToAdd2(-1);
				currentUser.setPromotionsToAdd(-1);
			} else if (params[1] == 5) {
				currentUser.setComUpToAdd(params[0]);
				currentUser.setComUpToAdd2(params[2]);
				currentUser.setUpvotedIdeasToAdd(-1);
				currentUser.setDownvotedIdeasToAdd(-1);
				currentUser.setJunkedIdeasToAdd(-1);
				currentUser.setComDownToAdd(-1);
				currentUser.setComDownToAdd2(-1);
				currentUser.setComPromToAdd(-1);
				currentUser.setComJunkToAdd(-1);
				currentUser.setComJunkToAdd2(-1);
				currentUser.setPromotionsToAdd(-1);
			} else if (params[1] == 6) {
				currentUser.setComDownToAdd(params[0]);
				currentUser.setComDownToAdd2(params[2]);
				currentUser.setUpvotedIdeasToAdd(-1);
				currentUser.setDownvotedIdeasToAdd(-1);
				currentUser.setJunkedIdeasToAdd(-1);
				currentUser.setComUpToAdd(-1);
				currentUser.setComUpToAdd2(-1);
				currentUser.setComPromToAdd(-1);
				currentUser.setComJunkToAdd(-1);
				currentUser.setComJunkToAdd2(-1);
				currentUser.setPromotionsToAdd(-1);
			} else if (params[1] == 7) {
				currentUser.setComPromToAdd(params[2]);
				currentUser.setUpvotedIdeasToAdd(-1);
				currentUser.setDownvotedIdeasToAdd(-1);
				currentUser.setJunkedIdeasToAdd(-1);
				currentUser.setComUpToAdd(-1);
				currentUser.setComUpToAdd2(-1);
				currentUser.setComDownToAdd(-1);
				currentUser.setComDownToAdd2(-1);
				currentUser.setComJunkToAdd(-1);
				currentUser.setComJunkToAdd2(-1);
				currentUser.setPromotionsToAdd(-1);
			} else if (params[1] == 8) {
				currentUser.setComJunkToAdd(params[0]);
				currentUser.setComJunkToAdd2(params[2]);
				currentUser.setUpvotedIdeasToAdd(-1);
				currentUser.setDownvotedIdeasToAdd(-1);
				currentUser.setJunkedIdeasToAdd(-1);
				currentUser.setComUpToAdd(-1);
				currentUser.setComUpToAdd2(-1);
				currentUser.setComDownToAdd(-1);
				currentUser.setComDownToAdd2(-1);
				currentUser.setComPromToAdd(-1);
				currentUser.setPromotionsToAdd(-1);
			} else if (params[1] == 9) {
				currentUser.setPromotionsToAdd(params[0]);
				currentUser.setUpvotedIdeasToAdd(-1);
				currentUser.setDownvotedIdeasToAdd(-1);
				currentUser.setJunkedIdeasToAdd(-1);
				currentUser.setComUpToAdd(-1);
				currentUser.setComUpToAdd2(-1);
				currentUser.setComDownToAdd(-1);
				currentUser.setComDownToAdd2(-1);
				currentUser.setComPromToAdd(-1);
				currentUser.setComJunkToAdd(-1);
				currentUser.setComJunkToAdd2(-1);
				//promTxt.setText(currentUser.getPromotions().toString());
			}

			try {
				endpoint.updateUser(currentUser).execute();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			new RetrieveUser().execute();

			return null;
		}
	}
	private class UpdateIdea extends AsyncTask<Integer, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (showReplyDialog) {
				callReplyDialog();
			}
		}

		@Override
		protected Void doInBackground(Integer... params) {

			IdeaHolder newIdea = new IdeaHolder(ideaArray.get(params[0]));

			Ideaendpoint.Builder builder = new Ideaendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);

			builder = CloudEndpointUtils.updateBuilder(builder);

			Ideaendpoint endpoint = builder.build();

			if (params[1] == 1) {
				newIdea.addToUpvotes(1);
			} else if (params[1] == 2) {
				newIdea.addToDownvotes(1);
			} else if (params[1] == 3) {
				newIdea.addToPromotions(1);
			} else if (params[1] == 4) {
				newIdea.addToJunk(1);
			} else if (params[1] == 5) {
				while (showReplyDialog) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(postReply) {
					postReply = false;
					if(currentUser != null) {
					newIdea.addAComment(replyText.getText().toString(),
							currentUser.getUsername());
					} else {
						newIdea.addAComment(replyText.getText().toString(),
								"Anonymous");
					}
				}
			} else if (params[1] == 6) {
				newIdea.addToComUpvotes(1, params[2]);
			} else if (params[1] == 7) {
				newIdea.addToComDownvotes(1, params[2]);
			} else if (params[1] == 8) {
				newIdea.addToComPromotions(1, params[2]);
			} else if (params[1] == 9) {
				newIdea.addToComJunk(1, params[2]);
			}

			try {
				endpoint.updateIdea(newIdea.getIdea()).execute();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			newIdea.cleanUp();

			return null;
		}
	}
	private class PostIdea extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {

			IdeaHolder newIdeaHolder;
			
			if(username != null) {
				newIdeaHolder = new IdeaHolder(ideaText.getText()
					.toString(), username.getText().toString(), ideaCategories
					.getSelectedItem().toString());
			} else {
				newIdeaHolder = new IdeaHolder(ideaText.getText()
						.toString(), "Anonymous", ideaCategories
						.getSelectedItem().toString());
			}

			Ideaendpoint.Builder builder = new Ideaendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);

			builder = CloudEndpointUtils.updateBuilder(builder);

			Ideaendpoint endpoint = builder.build();

			try {
				endpoint.insertIdea(newIdeaHolder.getIdea()).execute();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}
	}
	private class RetrieveIdeas extends
			AsyncTask<Void, Void, CollectionResponseIdea> {

		// CAST THE LINEARLAYOUT HOLDING THE MAIN PROGRESS (SPINNER)
		RelativeLayout loadingCircle = (RelativeLayout) findViewById(R.id.rlLoadingCircle);

		@Override
		protected void onPreExecute() {
			// SHOW THE SPINNER WHILE LOADING FEEDS
			loadingCircle.setVisibility(View.VISIBLE);
		}

		@Override
		protected CollectionResponseIdea doInBackground(Void... params) {

			Ideaendpoint.Builder endpointBuilder = new Ideaendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);

			endpointBuilder = CloudEndpointUtils.updateBuilder(endpointBuilder);

			CollectionResponseIdea result;

			Ideaendpoint endpoint = endpointBuilder.build();

			try {
				result = endpoint.listIdea().execute();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = null;
			}
			return result;
		}

		@Override
		protected void onPostExecute(CollectionResponseIdea result) {

			if (result == null || result.getItems() == null
					|| result.getItems().size() < 1) {
				if (result == null) {
					Toast.makeText(IdeaWall.this, "Retrieving ideas failed",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(IdeaWall.this, "No ideas found",
							Toast.LENGTH_SHORT).show();
				}

				return;
			}
			
			ideaArray = new ArrayList<Idea>();

			for (Idea idea : result.getItems()) {
				ideaArray.add(idea);
			}

			Mergesort.sort(ideaArray);

			newerSortedArray = new ArrayList<Idea>(
					Arrays.asList(Mergesort.newerIdeas));
			topSortedArray = new ArrayList<Idea>(
					Arrays.asList(Mergesort.topIdeas));
			hotSortedArray = new ArrayList<Idea>(
					Arrays.asList(Mergesort.hotIdeas));
			catSortedArray = Mergesort.catIdeas;

			switch (currentArray) {
			case 0:
				ideaList = createAdapter(newerSortedArray);
				break;
			case 1:
				ideaTopFilter.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub
						Mergesort.topSort(ideaTopFilter.getSelectedItem().toString());
						topSortedArray = Mergesort.topFilteredIdeas;
						ideaList = createAdapter(topSortedArray);
						cardsList.setAdapter(ideaList);
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub
						
					}
					
				});
				ideaList = createAdapter(topSortedArray);
				break;
			case 2:
				ideaList = createAdapter(hotSortedArray);
				break;
			case 3:
				ideaCatFilter.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub
						Mergesort.catSort(ideaCatFilter.getSelectedItem().toString());
						catSortedArray = Mergesort.catIdeas;
						ideaList = createAdapter(catSortedArray);
						cardsList.setAdapter(ideaList);
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub
						
					}
					
				});
				ideaList = createAdapter(catSortedArray);
				break;
			default:
				ideaList = createAdapter(newerSortedArray);
				break;
			}
			cardsList.setAdapter(ideaList);

			// HIDE THE SPINNER AFTER LOADING FEEDS
			loadingCircle.setVisibility(View.GONE);
		}
	}
	private class RegisterUser extends AsyncTask<Void, Void, Void> {

		/**
		 * Calls appropriate CloudEndpoint to indicate that user checked into a
		 * place.
		 * 
		 * @param params
		 *            the place where the user is checking in.
		 */
		@Override
		protected Void doInBackground(Void... params) {
			User newUser = new User();

			// Set the ID of the store where the user is.
			newUser.setUsername(username.getText().toString());
			newUser.setPassword(password.getText().toString());
			newUser.setEmail(email.getText().toString());
			newUser.setRegisterDate(new DateTime(new Date()));
			ArrayList<Boolean> tempBol = new ArrayList<Boolean>();
			tempBol.add(false);
			newUser.setUpvotedIdeas(tempBol);
			newUser.setUpvotedIdeasToAdd(-1);
			newUser.setDownvotedIdeas(tempBol);
			newUser.setDownvotedIdeasToAdd(-1);
			//newUser.setPromotedIdeas(tempBol);
			//newUser.setPromotedIdeasToAdd(-1);
			newUser.setPromotions(10);
			newUser.setPromotionsToAdd(-1);
			newUser.setJunkedIdeas(tempBol);
			newUser.setJunkedIdeasToAdd(-1);

			Userendpoint.Builder builder = new Userendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);

			builder = CloudEndpointUtils.updateBuilder(builder);

			Userendpoint endpoint = builder.build();

			try {
				endpoint.insertUser(newUser).execute();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}
	}
	private class LoginUser extends
			AsyncTask<Void, Void, CollectionResponseUser> {

		@Override
		protected CollectionResponseUser doInBackground(Void... params) {

			Userendpoint.Builder endpointBuilder = new Userendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);

			endpointBuilder = CloudEndpointUtils.updateBuilder(endpointBuilder);

			CollectionResponseUser result;

			Userendpoint endpoint = endpointBuilder.build();

			try {
				result = endpoint.listUser().execute();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = null;
			}
			return result;
		}

		@Override
		protected void onPostExecute(CollectionResponseUser result) {

			if (result == null || result.getItems() == null
					|| result.getItems().size() < 1) {
				if (result == null) {
					Toast.makeText(IdeaWall.this, "Retrieving users failed",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(IdeaWall.this, "No users found",
							Toast.LENGTH_SHORT).show();
				}

				return;
			}

			List<User> users = result.getItems();

			for (User user : users) {
				if (user.getUsername().equals(username.getText().toString())
						&& user.getPassword().equals(
								password.getText().toString())) {
				Toast.makeText(IdeaWall.this, "Logged in successfully",
							Toast.LENGTH_SHORT).show();
					isLoggedIn = true;
					setContentView(loggedIn);
					setupList(isLoggedIn);
					currentUser = user;
					if (currentUser.getUpvotedIdeas() != null)
						currentUserData.upvoteData = currentUser
								.getUpvotedIdeas();
					if (currentUser.getDownvotedIdeas() != null)
						currentUserData.downvoteData = currentUser
								.getDownvotedIdeas();
					/*if (currentUser.getPromotedIdeas() != null)
						currentUserData.promoteData = currentUser
								.getPromotedIdeas();*/
					if (currentUser.getJunkedIdeas() != null)
						currentUserData.junkData = currentUser.getJunkedIdeas();
					if (currentUser.getCommentsUpvoted() != null)
						currentUserData.comUpvoteData = currentUser
								.getCommentsUpvoted();
					if (currentUser.getCommentsDownvoted() != null)
						currentUserData.comDownvoteData = currentUser
								.getCommentsDownvoted();
					if (currentUser.getCommentsPromoted() != null)
						currentUserData.comPromoteData = currentUser
								.getCommentsPromoted();
					if (currentUser.getCommentsJunked() != null)
						currentUserData.comJunkData = currentUser
								.getCommentsJunked();
					if(currentUser.getPromotions() != null)
						currentUserData.promotions = currentUser.getPromotions();
					promTxt.setText(currentUser.getPromotions().toString());
					new RetrieveIdeas().execute();
					return;
				}
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Toast.makeText(IdeaWall.this, "Log in failed", Toast.LENGTH_SHORT)
					.show();

		}
	}
	private class RetrieveUser extends
			AsyncTask<Void, Void, CollectionResponseUser> {

		@Override
		protected CollectionResponseUser doInBackground(Void... params) {

			Userendpoint.Builder endpointBuilder = new Userendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);

			endpointBuilder = CloudEndpointUtils.updateBuilder(endpointBuilder);

			CollectionResponseUser result;

			Userendpoint endpoint = endpointBuilder.build();

			try {
				result = endpoint.listUser().execute();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = null;
			}
			return result;
		}

		@Override
		protected void onPostExecute(CollectionResponseUser result) {

			if (result == null || result.getItems() == null
					|| result.getItems().size() < 1) {
				if (result == null) {
					Toast.makeText(IdeaWall.this, "Retrieving users failed",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(IdeaWall.this, "No users found",
							Toast.LENGTH_SHORT).show();
				}
				updating = false;
				return;
			}
			
			if(currentUser == null) {
				updating = false;
				return;
			}

			List<User> users = result.getItems();

			for (User user : users) {
				if (user.getUsername().equals(currentUser.getUsername())
						&& user.getPassword().equals(currentUser.getPassword())) {
					currentUser = user;
					currentUserData.upvoteData = currentUser.getUpvotedIdeas();
					currentUserData.downvoteData = currentUser
							.getDownvotedIdeas();
					/*currentUserData.promoteData = currentUser
							.getPromotedIdeas();*/
					currentUserData.junkData = currentUser.getJunkedIdeas();
					currentUserData.promotions = currentUser.getPromotions();
					updating = false;
					return;
				}
			}

		}
	}
	private class RetrieveDevice extends
			AsyncTask<Void, Void, CollectionResponseDeviceInfo> {

		@Override
		protected CollectionResponseDeviceInfo doInBackground(Void... params) {

			Deviceinfoendpoint.Builder endpointBuilder = new Deviceinfoendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);

			endpointBuilder = CloudEndpointUtils.updateBuilder(endpointBuilder);

			CollectionResponseDeviceInfo result;

			Deviceinfoendpoint endpoint = endpointBuilder.build();

			try {
				result = endpoint.listDeviceInfo().execute();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = null;
			}
			return result;
		}

		@Override
		protected void onPostExecute(CollectionResponseDeviceInfo result) {

			if (result == null || result.getItems() == null
					|| result.getItems().size() < 1) {
				if (result == null) {
					Toast.makeText(IdeaWall.this, "Retrieving devices failed",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(IdeaWall.this, "No devices found",
							Toast.LENGTH_SHORT).show();
				}

				return;
			}

			List<DeviceInfo> devices = result.getItems();

			for (DeviceInfo device : devices) {
				if (device.getDeviceRegistrationID().equals(currentDeviceID)) {
					currentDevice = device;
					if (currentDevice.getUpvotedIdeas() != null)
						currentDeviceData.upvoteData = currentDevice
								.getUpvotedIdeas();
					if (currentDevice.getDownvotedIdeas() != null)
						currentDeviceData.downvoteData = currentDevice
								.getDownvotedIdeas();
					if (currentDevice.getPromotedIdeas() != null)
						currentDeviceData.promoteData = currentDevice
								.getPromotedIdeas();
					if (currentDevice.getJunkedIdeas() != null)
						currentDeviceData.junkData = currentDevice
								.getJunkedIdeas();
					if (currentDevice.getCommentsUpvoted() != null)
						currentDeviceData.comUpvoteData = currentDevice
								.getCommentsUpvoted();
					if (currentDevice.getCommentsDownvoted() != null)
						currentDeviceData.comDownvoteData = currentDevice
								.getCommentsDownvoted();
					if (currentDevice.getCommentsJunked() != null)
						currentDeviceData.comJunkData = currentDevice
								.getCommentsJunked();
					updating = false;
					ideaList.notifyDataSetChanged();
					return;
				}
			}

		}
	}

	private void callLoginDialog() {
		logDialog = new Dialog(IdeaWall.this);
		logDialog.setContentView(R.layout.login);
		logDialog.setCancelable(true);
		logDialog.setOnKeyListener(new Dialog.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface arg0, int keyCode,
					KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					logDialog.dismiss();
				}
				return true;
			}
		});
		Button loginPop = (Button) logDialog.findViewById(R.id.bLoginPop);

		username = (EditText) logDialog.findViewById(R.id.etUsername);
		password = (EditText) logDialog.findViewById(R.id.etPassword);
		logDialog.show();

		loginPop.setOnClickListener(this);

	}
	private void callRegisterDialog() {
		regDialog = new Dialog(IdeaWall.this);
		regDialog.setContentView(R.layout.register);
		regDialog.setCancelable(true);
		regDialog.setOnKeyListener(new Dialog.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface arg0, int keyCode,
					KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					regDialog.dismiss();
				}
				return true;
			}
		});
		Button registerPop = (Button) regDialog.findViewById(R.id.bRegisterPop);

		username = (EditText) regDialog.findViewById(R.id.etUserReg);
		password = (EditText) regDialog.findViewById(R.id.etPassReg);
		email = (EditText) regDialog.findViewById(R.id.etEmail);
		registerPop.setOnClickListener(this);
		regDialog.show();

	}
	private void callPostDialog() {
		postDialog = new Dialog(IdeaWall.this);
		postDialog.setContentView(R.layout.post);
		postDialog.setCancelable(true);
		postDialog.setOnKeyListener(new Dialog.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface arg0, int keyCode,
					KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					postDialog.dismiss();
				}
				return true;
			}
		});

		Button postIdeaPop = (Button) postDialog
				.findViewById(R.id.bPostIdeaPop);

		ideaText = (EditText) postDialog.findViewById(R.id.etIdeaText);
		ideaCategories = (Spinner) postDialog.findViewById(R.id.sCategories);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.categories, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ideaCategories.setAdapter(adapter);
		postDialog.show();

		postIdeaPop.setOnClickListener(this);

	}
	private void callReplyDialog() {
		replyDialog = new Dialog(IdeaWall.this);
		replyDialog.setContentView(R.layout.reply);
		replyDialog.setCancelable(true);
		replyDialog.setOnKeyListener(new Dialog.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface arg0, int keyCode,
					KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					replyDialog.dismiss();
				}
				return true;
			}
		});
		replyDialog.setOnDismissListener(new Dialog.OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub
				showReplyDialog = false;
			}
			
		});
		Button replyPop = (Button) replyDialog.findViewById(R.id.bReplyPop);

		replyText = (EditText) replyDialog.findViewById(R.id.etReplyText);
		replyDialog.show();

		replyPop.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bLogin:
			callLoginDialog();
			break;
		case R.id.bRegister:
			callRegisterDialog();
			break;
		case R.id.bLoginPop:
			new LoginUser().execute();
			logDialog.dismiss();
			break;
		case R.id.bRegisterPop:
			new RegisterUser().execute();
			regDialog.dismiss();
			break;
		case R.id.bPostIdea:
			callPostDialog();
			break;
		case R.id.bPostIdeaPop:
			if (ideaText.getText().length() < 1) {
				Toast.makeText(IdeaWall.this, "Idea is empty",
						Toast.LENGTH_SHORT).show();
				break;
			}
			if (ideaText.getText().length() < 5) {
				Toast.makeText(IdeaWall.this, "Idea is too short",
						Toast.LENGTH_SHORT).show();
				break;
			}
			new PostIdea().execute();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			new RetrieveIdeas().execute();
			postDialog.dismiss();
			break;
		case R.id.bReplyPop:
			postReply = true;
			replyDialog.dismiss();
			break;
		case R.id.bRefresh:
			new RetrieveIdeas().execute();
			break;
		case R.id.bLogOut:
			Toast.makeText(IdeaWall.this, "Logged out successfully",
					Toast.LENGTH_SHORT).show();
			isLoggedIn = false;
			setContentView(notLoggedIn);
			setupList(isLoggedIn);
			currentUser = null;
			new RetrieveIdeas().execute();
			break;
		}
	}

	public boolean ideaChecked(int idea) {
		if (currentUser == null) {
			if (currentDeviceID == null) {
				return true;
			} else if (currentDeviceData.upvoteData != null
					&& currentDeviceData.upvoteData.size() > idea
					&& currentDeviceData.upvoteData.get(idea)) {
				return true;
			}
		} else if(currentUser.equals("1")) {
			return false;
		}
		if (currentUserData.upvoteData.size() > idea
				&& currentUserData.upvoteData.get(idea)) {
			return true;
		}
		return false;
	}
	public boolean ideaCrossed(int idea) {
		if (currentUser == null) {
			if (currentDeviceID == null) {
				return true;
			} else if (currentDeviceData.downvoteData != null
					&& currentDeviceData.downvoteData.size() > idea
					&& currentDeviceData.downvoteData.get(idea)) {
				return true;
			}
		} else if(currentUser.equals("1")) {
			return false;
		}
		if (currentUserData.downvoteData.size() > idea
				&& currentUserData.downvoteData.get(idea)) {
			return true;
		}
		return false;
	}
	public boolean ideaPromoted(int idea) {
		if (currentUser == null) {
			if (currentDeviceID == null) {
				return true;
			} else if (currentDeviceData.promoteData != null
					&& currentDeviceData.promoteData.size() > idea
					&& currentDeviceData.promoteData.get(idea)) {
				return true;
			}
		}
		if (currentUserData.promoteData.size() > idea
				&& currentUserData.promoteData.get(idea)) {
			return true;
		}
		return false;
	}
	public boolean ideaJunked(int idea) {
		if (currentUser == null) {
			if (currentDeviceID == null) {
				return false;
			} else if (currentDeviceData.junkData != null
					&& currentDeviceData.junkData.size() > idea
					&& currentDeviceData.junkData.get(idea)) {
				return true;
			}
		}
		if (currentUserData.junkData.size() > idea
				&& currentUserData.junkData.get(idea)) {
			return true;
		}
		return false;
	}
	public boolean commentChecked(int gp, int cp) {
		if (currentUser == null) {
			if (currentDeviceID == null) {
				return true;
			} else if (currentDeviceData.comUpvoteData != null
					&& currentDeviceData.comUpvoteData.size() > gp
					&& currentDeviceData.comUpvoteData.get(gp).length() > cp*2 // child position is doubled to account for commas
					&& currentDeviceData.comUpvoteData.get(gp).substring(cp*2, (cp*2)+1).equals("1")) {
				return true;
			}
		}
		if (currentUserData.comUpvoteData.size() > gp
				&& currentUserData.comUpvoteData.get(gp).length() > cp*2 // child position is doubled to account for commas
				&& currentUserData.comUpvoteData.get(gp).substring(cp*2, (cp*2)+1).equals("1")) {
			return true;
		}
		return false;
	}
	public boolean commentCrossed(int gp, int cp) {
		if (currentUser == null) {
			if (currentDeviceID == null) {
				return true;
			} else if (currentDeviceData.comDownvoteData != null
					&& currentDeviceData.comDownvoteData.size() > gp
					&& currentDeviceData.comDownvoteData.get(gp).length() > cp*2 // child position is doubled to account for commas
					&& currentDeviceData.comDownvoteData.get(gp).substring(cp*2, (cp*2)+1).equals("1")) {
				return true;
			}
		}
		if (currentUserData.comDownvoteData.size() > gp
				&& currentUserData.comDownvoteData.get(gp).length() > cp*2 // child position is doubled to account for commas
				&& currentUserData.comDownvoteData.get(gp).substring(cp*2, (cp*2)+1).equals("1")) {
			return true;
		}
		return false;
	}
	public boolean commentJunked(int gp, int cp) {
		if (currentUser == null) {
			if (currentDeviceID == null) {
				return false;
			} else if (currentDeviceData.comJunkData != null
					&& currentDeviceData.comJunkData.size() > gp
					&& currentDeviceData.comDownvoteData.get(gp).length() > cp*2 // child position is doubled to account for commas
					&& currentDeviceData.comDownvoteData.get(gp).substring(cp*2, (cp*2)+1).equals("1")) {
				return true;
			}
		}
		if (currentUserData.comJunkData.size() > gp
				&& currentUserData.comDownvoteData.get(gp).length() > cp*2 // child position is doubled to account for commas
				&& currentUserData.comDownvoteData.get(gp).substring(cp*2, (cp*2)+1).equals("1")) {
			return true;
		}
		return false;
	}
	
	public static int sortToRealPos(int sortPos) {
		Idea tempIdea = newerSortedArray.get(sortPos);
		switch (currentArray) {
		case 0:
			tempIdea = newerSortedArray.get(sortPos);
			break;
		case 1:
			tempIdea = topSortedArray.get(sortPos);
			break;
		case 2:
			tempIdea = hotSortedArray.get(sortPos);
			break;
		case 3:
			tempIdea = catSortedArray.get(sortPos);
			break;
		}
		for (int i = 0; i < ideaArray.size(); i++) {
			if (tempIdea.getIdea().equals(ideaArray.get(i).getIdea()))
				return i;
		}
		return sortPos;
	}
	public static int realToSortPos(int realPos) {
		Idea tempIdea = ideaArray.get(realPos);
		switch (currentArray) {
		case 0:
			for (int i = 0; i < newerSortedArray.size(); i++) {
				if (tempIdea.getIdea()
						.equals(newerSortedArray.get(i).getIdea()))
					return i;
			}
			break;
		case 1:
			for (int i = 0; i < topSortedArray.size(); i++) {
				if (tempIdea.getIdea().equals(topSortedArray.get(i).getIdea()))
					return i;
			}
			break;
		case 2:
			for (int i = 0; i < hotSortedArray.size(); i++) {
				if (tempIdea.getIdea().equals(hotSortedArray.get(i).getIdea()))
					return i;
			}
		case 3:
			for (int i = 0; i < catSortedArray.size(); i++) {
				if (tempIdea.getIdea().equals(catSortedArray.get(i).getIdea()))
					return i;
			}
		}
		return realPos;
	}

	public class UserData {

		List<Boolean> upvoteData;
		List<Boolean> downvoteData;
		List<Boolean> promoteData;
		List<Boolean> junkData;
		List<String> comUpvoteData;
		List<String> comDownvoteData;
		List<Boolean> comPromoteData;
		List<String> comJunkData;
		int promotions;

		public UserData() {
			upvoteData = new ArrayList<Boolean>();
			downvoteData = new ArrayList<Boolean>();
			promoteData = new ArrayList<Boolean>();
			junkData = new ArrayList<Boolean>();
			comUpvoteData = new ArrayList<String>();
			comDownvoteData = new ArrayList<String>();
			comPromoteData = new ArrayList<Boolean>();
			comJunkData = new ArrayList<String>();
			promotions = 0;
		}

	}
	public class DeviceData {

		List<Boolean> upvoteData;
		List<Boolean> downvoteData;
		List<Boolean> promoteData;
		List<Boolean> junkData;
		List<String> comUpvoteData;
		List<String> comDownvoteData;
		List<String> comJunkData;

		public DeviceData() {
			upvoteData = new ArrayList<Boolean>();
			downvoteData = new ArrayList<Boolean>();
			promoteData = new ArrayList<Boolean>();
			junkData = new ArrayList<Boolean>();
			comUpvoteData = new ArrayList<String>();
			comDownvoteData = new ArrayList<String>();
			comJunkData = new ArrayList<String>();

		}

	}

	public TabHost getTabHost() {
		return tabHost;
	}

}
