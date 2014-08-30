package com.testappengine;


import com.testappengine.AnimatedExpandableListView.AnimatedExpandableListAdapter;
import com.testappengine.IdeaWall.DeviceData;
import com.testappengine.IdeaWall.UserData;
import com.testappengine.ideaendpoint.model.Idea;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CardsAdapter extends AnimatedExpandableListAdapter {

	private List<Idea> items;
	private final OnClickListener itemButtonClickListener;
	private final Context context;
	private UserData currentUserData;
	private DeviceData currentDeviceData;
	public static boolean onClick, onScroll;

	public CardsAdapter(Context context, List<Idea> items,
			OnClickListener itemButtonClickListener, UserData currentUserData,
			DeviceData currentDeviceData) {
		this.context = context;
		this.items = items;
		this.itemButtonClickListener = itemButtonClickListener;
		this.currentUserData = currentUserData;
		this.currentDeviceData = currentDeviceData;
		onClick = true;
	}

	@Override
	public int getGroupCount() {
		return items.size();
	}

	@Override
	public int getRealChildrenCount(int groupPosition) {
		if (items.size() <= groupPosition)
			return 0;
		List<String> tempList = items.get(groupPosition).getComments();
		return tempList.size();
	}

	@Override
	public String getGroup(int groupPosition) {
		return items.get(groupPosition).getIdea();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return items.get(groupPosition).getComments().get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getGroupView(int sortPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		ViewHolder holder;
		
		int position = sortPosition;
		
		

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.list_item_card, null);

			holder = new ViewHolder();
			holder.ideaText = (TextView) convertView.findViewById(R.id.tvIdea);
			holder.usernameText = (TextView) convertView
					.findViewById(R.id.tvUsernameCard);
			holder.upvoteText = (TextView) convertView
					.findViewById(R.id.tvUpvotes);
			holder.downvoteText = (TextView) convertView
					.findViewById(R.id.tvDownvotes);
			holder.percentText = (TextView) convertView
					.findViewById(R.id.tvVotePercent);
			holder.dateText = (TextView) convertView
					.findViewById(R.id.tvDatePosted);
			holder.upvoteButton = (ImageButton) convertView
					.findViewById(R.id.ibCheckmark);
			holder.downvoteButton = (ImageButton) convertView
					.findViewById(R.id.ibCrossmark);
			holder.promotionButton = (Button) convertView
					.findViewById(R.id.bPromotionCard);
			holder.junkButton = (Button) convertView
					.findViewById(R.id.bJunkButton);
			holder.replyButton = (Button) convertView.findViewById(R.id.bReply);
			holder.commentButton = (Button) convertView
					.findViewById(R.id.bViewComments);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Idea tempIdea = items.get(position);

		holder.ideaText.setText("\t\t\t\t" + tempIdea.getIdea());
		holder.usernameText.setText(tempIdea.getPoster());
		holder.upvoteText.setText(tempIdea.getUpvotes().toString());
		holder.downvoteText.setText(tempIdea.getDownvotes().toString());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yy h:mm aa",
				Locale.getDefault());
		String dateStr = tempIdea.getPostDate().toString().replace("T", " ")
				.substring(0, 23).concat("+000");
		Date result;
		try {
			result = sdf.parse(dateStr);
			holder.dateText.setText(sdf2.format(result).toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		int votePercent;
		if (tempIdea.getUpvotes() == 0 && tempIdea.getDownvotes() == 0) {
			votePercent = 100;
		} else {
			votePercent = (int) ((((float) (tempIdea.getUpvotes())) / ((float) (tempIdea
					.getUpvotes() + tempIdea.getDownvotes()))) * 100.0);
		}
		if (items.get(position).getDownvotes() != 0) {
			holder.percentText.setText(Integer.toString(votePercent) + "%");
		} else {
			holder.percentText.setText("100%");
		}
		if (votePercent > 89) {
			holder.percentText.setTextColor(Color.rgb(51, 136, 51));
		} else if (votePercent > 79) {
			holder.percentText.setTextColor(Color.rgb(85, 170, 85));
		} else if (votePercent > 69) {
			holder.percentText.setTextColor(Color.rgb(221, 221, 34));
		} else if (votePercent > 59) {
			holder.percentText.setTextColor(Color.rgb(221, 136, 34));
		} else {
			holder.percentText.setTextColor(Color.rgb(170, 85, 85));
		}
		
		ArrayList<String> tempStr = (ArrayList)tempIdea.getComments();
		if(tempStr != null)
			holder.commentButton.setText("Comments (" + tempStr.size() + ")");
		else
			holder.commentButton.setText("Comments (0)");

		if (itemButtonClickListener != null) {
			holder.upvoteButton.setOnClickListener(itemButtonClickListener);
			holder.downvoteButton.setOnClickListener(itemButtonClickListener);
			holder.promotionButton.setOnClickListener(itemButtonClickListener);
			holder.junkButton.setOnClickListener(itemButtonClickListener);
			holder.replyButton.setOnClickListener(itemButtonClickListener);
			holder.commentButton.setOnClickListener(itemButtonClickListener);
			position = IdeaWall.sortToRealPos(sortPosition);
			if ((currentUserData.upvoteData.size() > position && currentUserData.upvoteData
					.get(position))
					|| ((IdeaWall.currentUser == null && currentDeviceData.upvoteData != null) && (currentDeviceData.upvoteData
							.size() > position && currentDeviceData.upvoteData
							.get(position)))) {
				holder.upvoteButton.setImageResource(R.drawable.checkmark);
			} else {
				holder.upvoteButton
						.setImageResource(R.drawable.checkmarkgreyed);
			}
			if ((currentUserData.downvoteData.size() > position && currentUserData.downvoteData
					.get(position))
					|| ((IdeaWall.currentUser == null && currentDeviceData.downvoteData != null) && (currentDeviceData.downvoteData
							.size() > position && currentDeviceData.downvoteData
							.get(position)))) {
				holder.downvoteButton.setImageResource(R.drawable.cross);
			} else {
				holder.downvoteButton.setImageResource(R.drawable.crossgreyed);
			}
			if ((currentUserData.junkData.size() > position && currentUserData.junkData
					.get(position))
					|| ((IdeaWall.currentUser == null && currentDeviceData.junkData != null) && (currentDeviceData.junkData
							.size() > position && currentDeviceData.junkData
							.get(position)))) {
				holder.junkButton.setBackgroundColor(Color.rgb(168, 137, 88));
			} else {
				holder.junkButton.setBackgroundColor(Color.WHITE);
			}
		}

		return convertView;
	}

	@Override
	public View getRealChildView(int sortPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		int groupPosition = sortPosition;
		
		CommentHolder holder;

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.comment, null);

			holder = new CommentHolder();
			holder.commentText = (TextView) convertView
					.findViewById(R.id.tvComment);
			holder.usernameText = (TextView) convertView
					.findViewById(R.id.tvUsernameCard);
			holder.percentText = (TextView) convertView
					.findViewById(R.id.tvVotePercent);
			holder.dateText = (TextView) convertView
					.findViewById(R.id.tvDatePosted);
			holder.upvoteButton = (ImageButton) convertView
					.findViewById(R.id.ibComCheckmark);
			holder.downvoteButton = (ImageButton) convertView
					.findViewById(R.id.ibComCrossmark);
			holder.promotionButton = (Button) convertView
					.findViewById(R.id.bComPromotionCard);
			holder.junkButton = (Button) convertView
					.findViewById(R.id.bComJunkButton);

			convertView.setTag(holder);

		} else {
			holder = (CommentHolder) convertView.getTag();
		}

		Idea tempIdea = items.get(groupPosition);

		holder.commentText.setText("\t\t\t\t" + tempIdea.getComments().get(childPosition));
		holder.usernameText.setText(tempIdea.getComUsernames().get(
				childPosition));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yy",
				Locale.getDefault());
		String dateStr = tempIdea.getPostDate().toString().replace("T", " ")
				.substring(0, 23).concat("+000");
		Date result;
		try {
			result = sdf.parse(dateStr);
			holder.dateText.setText(sdf2.format(result).toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		int votePercent;
		if (tempIdea.getUpvotes() == 0 && tempIdea.getDownvotes() == 0) {
			votePercent = 100;
		} else {
			votePercent = (int) ((((float) (tempIdea.getUpvotes())) / ((float) (tempIdea
					.getUpvotes() + tempIdea.getDownvotes()))) * 100.0);
		}
		if (items.get(groupPosition).getDownvotes() != 0) {
			holder.percentText.setText(Integer.toString(votePercent) + "%");
		} else {
			holder.percentText.setText("100%");
		}
		if (votePercent > 89) {
			holder.percentText.setTextColor(Color.rgb(51, 136, 51));
		} else if (votePercent > 79) {
			holder.percentText.setTextColor(Color.rgb(85, 170, 85));
		} else if (votePercent > 69) {
			holder.percentText.setTextColor(Color.rgb(221, 221, 34));
		} else if (votePercent > 59) {
			holder.percentText.setTextColor(Color.rgb(221, 136, 34));
		} else {
			holder.percentText.setTextColor(Color.rgb(170, 85, 85));
		}

		if (itemButtonClickListener != null) {
			holder.upvoteButton.setOnClickListener(itemButtonClickListener);
			holder.downvoteButton.setOnClickListener(itemButtonClickListener);
			holder.promotionButton.setOnClickListener(itemButtonClickListener);
			holder.junkButton.setOnClickListener(itemButtonClickListener);
			groupPosition = IdeaWall.sortToRealPos(sortPosition);
			if ((currentUserData.comUpvoteData.size() > childPosition 
						&& currentUserData.comUpvoteData.get(childPosition).length() > childPosition*2 // child position is doubled to account for commas
						&& currentDeviceData.comUpvoteData.get(groupPosition).substring((childPosition*2), (childPosition*2)+1).equals("1"))
					|| ((currentDeviceData.comUpvoteData != null) 
						&& (currentDeviceData.comUpvoteData.size() > groupPosition 
						&& currentDeviceData.comUpvoteData.get(groupPosition).length() > childPosition*2 // child position is doubled to account for commas
						&& currentDeviceData.comUpvoteData.get(groupPosition).substring((childPosition*2), (childPosition*2)+1).equals("1")))) {
				holder.upvoteButton.setImageResource(R.drawable.uparrow);
			} else {
				holder.upvoteButton
						.setImageResource(R.drawable.uparrowgrey);
			}
			if ((currentUserData.comDownvoteData.size() > childPosition 
						&& currentUserData.comDownvoteData.get(childPosition).length() > childPosition*2 // child position is doubled to account for commas
						&& currentUserData.comDownvoteData.get(groupPosition).substring((childPosition*2), (childPosition*2)+1).equals("1"))
					|| ((currentDeviceData.comDownvoteData != null) 
						&& (currentDeviceData.comDownvoteData.size() > groupPosition 
						&& currentDeviceData.comDownvoteData.get(groupPosition).length() > childPosition*2 // child position is doubled to account for commas
						&& currentDeviceData.comDownvoteData.get(groupPosition).substring((childPosition*2), (childPosition*2)+1).equals("1")))) {
				holder.downvoteButton.setImageResource(R.drawable.downarrow);
			} else {
				holder.downvoteButton.setImageResource(R.drawable.downarrowgrey);
			}
			if ((currentUserData.comJunkData.size() > childPosition 
						&& currentUserData.comJunkData.get(childPosition).length() > childPosition*2 // child position is doubled to account for commas
						&& currentDeviceData.comJunkData.get(groupPosition).substring((childPosition*2), (childPosition*2)+1).equals("1"))
					|| ((currentDeviceData.comJunkData != null) 
						&& (currentDeviceData.comJunkData.size() > groupPosition 
						&& currentDeviceData.comJunkData.get(groupPosition).length() > childPosition*2 // child position is doubled to account for commas
						&& currentDeviceData.comJunkData.get(groupPosition).substring((childPosition*2), (childPosition*2)+1).equals("1")))) {
				holder.junkButton.setBackgroundColor(Color.rgb(168, 137, 88));
			} else {
				holder.junkButton.setBackgroundColor(Color.TRANSPARENT);
			}
		}

		return convertView;
	}

	private static class ViewHolder {
		private TextView ideaText;
		private TextView usernameText;
		private TextView upvoteText;
		private TextView downvoteText;
		private TextView percentText;
		private TextView dateText;
		private ImageButton upvoteButton;
		private ImageButton downvoteButton;
		private Button promotionButton;
		private Button junkButton;
		private Button replyButton;
		private Button commentButton;
	}

	private static class CommentHolder {
		private TextView commentText;
		private TextView usernameText;
		private TextView percentText;
		private TextView dateText;
		private ImageButton upvoteButton;
		private ImageButton downvoteButton;
		private Button promotionButton;
		private Button junkButton;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
