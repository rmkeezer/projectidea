package com.testappengine;

import com.testappengine.EMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.datanucleus.query.JPACursorHelper;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

@Api(name = "userendpoint", namespace = @ApiNamespace(ownerDomain = "testappengine.com", ownerName = "testappengine.com", packagePath = ""))
public class UserEndpoint {

	/**
	 * This method lists all the entities inserted in datastore. It uses HTTP
	 * GET method and paging support.
	 * 
	 * @return A CollectionResponse class containing the list of all entities
	 *         persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listUser")
	public CollectionResponse<User> listUser(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<User> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from User as User");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<User>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and
			// accomodate
			// for lazy fetch.
			for (User obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<User> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET
	 * method.
	 * 
	 * @param id
	 *            the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getUser")
	public User getUser(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		User user = null;
		try {
			user = mgr.find(User.class, id);
		} finally {
			mgr.close();
		}
		return user;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity
	 * already exists in the datastore, an exception is thrown. It uses HTTP
	 * POST method.
	 * 
	 * @param user
	 *            the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertUser")
	public User insertUser(User user) {
		EntityManager mgr = getEntityManager();
		try {
			mgr.persist(user);
		} finally {
			mgr.close();
		}
		return user;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does
	 * not exist in the datastore, an exception is thrown. It uses HTTP PUT
	 * method.
	 * 
	 * @param user
	 *            the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateUser")
	public User updateUser(User user) {
		EntityManager mgr = getEntityManager();
		EntityTransaction transaction = mgr.getTransaction();
		User updatedUser = new User();
		try {
			transaction.begin();
			updatedUser = mgr.find(User.class, user.getKey().getId());
			int key = user.getUpvotedIdeasToAdd();
			if (key != -1) {

				List<Boolean> tempArr = updatedUser.getUpvotedIdeas();
				if (tempArr.size() <= key)
					for (int i = tempArr.size(); i <= key; i++)
						tempArr.add(false);
				tempArr.set(key, true);
			}
			key = user.getDownvotedIdeasToAdd();
			if (key != -1) {
				List<Boolean> tempArr = updatedUser.getDownvotedIdeas();
				if (tempArr.size() <= key)
					for (int i = tempArr.size(); i <= key; i++)
						tempArr.add(false);
				tempArr.set(key, true);
			}
			/*key = user.getPromotedIdeasToAdd();
			if (key != -1) {
				List<Boolean> tempArr = updatedUser.getPromotedIdeas();
				if (tempArr.size() <= key)
					for (int i = tempArr.size(); i <= key; i++)
						tempArr.add(false);
				tempArr.set(key, true);
			}*/
			key = user.getJunkedIdeasToAdd();
			if (key != -1) {
				List<Boolean> tempArr = updatedUser.getJunkedIdeas();
				if (tempArr.size() <= key)
					for (int i = tempArr.size(); i <= key; i++)
						tempArr.add(false);
				tempArr.set(key, true);
			}
			key = user.getPromotionsToAdd();
			if (key != -1) {
				updatedUser.setPromotions(updatedUser.getPromotions() - 1);
			}
			// Uses a string to store upvote data per comment section.
			// Google Datastore does not support Maps or Matrices.
			key = user.getComUpToAdd();
			int key2 = user.getComUpToAdd2();
			if (key != -1 && key2 != -1) {
				List<String> tempArr = updatedUser.getCommentsUpvoted();
				if (tempArr.size() <= key) {
					for (int i=tempArr.size(); i<=key; i++) {
						tempArr.add("0");
					}
				}
				String filler = "";
				String comStr = tempArr.get(key);
				if (comStr.length() <= key2*2) {
					for (int i=comStr.length(); i<=key2*2; i+=2) {
						filler += ",0";
					}
					if (filler.length() == 0 && comStr.length() == 0) {
						filler += "1";
					} else if (filler.length() == 0) {
						filler += ",1";
					} else {
						filler = filler.substring(0, filler.length()-1) + "1";
					}
					tempArr.set(key, comStr + filler);
				} else {
					if(key2 == 0) {
						tempArr.set(key, "1" + comStr.substring(1));
					} else {
						tempArr.set(key, comStr.substring(0, (key2*2)) + "1" + comStr.substring((key2*2)+1));
					}
				}
			}
			// Uses a string to store upvote data per comment section.
			// Google Datastore does not support Maps or Matrices.
			key = user.getComDownToAdd();
			key2 = user.getComDownToAdd2();
			if (key != -1 && key2 != -1) {
				List<String> tempArr = updatedUser.getCommentsDownvoted();
				if (tempArr.size() <= key) {
					for (int i=tempArr.size(); i<=key; i++) {
						tempArr.add("0");
					}
				}
				String filler = "";
				String comStr = tempArr.get(key);
				if (comStr.length() <= key2*2) {
					for (int i=comStr.length(); i<=key2*2; i+=2) {
						filler += ",0";
					}
					if (filler.length() == 0 && comStr.length() == 0) {
						filler += "1";
					} else if (filler.length() == 0) {
						filler += ",1";
					} else {
						filler = filler.substring(0, filler.length()-1) + "1";
					}
					tempArr.set(key, comStr + filler);
				} else {
					if(key2 == 0) {
						tempArr.set(key, "1" + comStr.substring(1));
					} else {
						tempArr.set(key, comStr.substring(0, (key2*2)) + "1" + comStr.substring((key2*2)+1));
					}
				}
			}
			key = user.getComPromToAdd();
			if (key != -1) {
				List<Boolean> tempArr = updatedUser.getCommentsPromoted();
				if (tempArr.size() <= key)
					for (int i = tempArr.size(); i <= key; i++)
						tempArr.add(false);
				tempArr.set(key, true);
			}
			// Uses a string to store upvote data per comment section.
			// Google Datastore does not support Maps or Matrices.
			key = user.getComJunkToAdd();
			key2 = user.getComJunkToAdd2();
			if (key != -1 && key2 != -1) {
				List<String> tempArr = updatedUser.getCommentsJunked();
				if (tempArr.size() <= key) {
					for (int i=tempArr.size(); i<=key; i++) {
						tempArr.add("0");
					}
				}
				String filler = "";
				String comStr = tempArr.get(key);
				if (comStr.length() <= key2*2) {
					for (int i=comStr.length(); i<=key2*2; i+=2) {
						filler += ",0";
					}
					if (filler.length() == 0 && comStr.length() == 0) {
						filler += "1";
					} else if (filler.length() == 0) {
						filler += ",1";
					} else {
						filler = filler.substring(0, filler.length()-1) + "1";
					}
					tempArr.set(key, comStr + filler);
				} else {
					if(key2 == 0) {
						tempArr.set(key, "1" + comStr.substring(1));
					} else {
						tempArr.set(key, comStr.substring(0, (key2*2)) + "1" + comStr.substring((key2*2)+1));
					}
				}
			}
			transaction.commit();
			mgr.refresh(updatedUser);
		} finally {
			mgr.close();
		}
		return user;
	}

	/**
	 * This method removes the entity with primary key id. It uses HTTP DELETE
	 * method.
	 * 
	 * @param id
	 *            the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeUser")
	public void removeUser(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			User user = mgr.find(User.class, id);
			mgr.remove(user);
		} finally {
			mgr.close();
		}
	}

	private boolean containsUser(User user) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			User item = mgr.find(User.class, user.getKey());
			if (item == null) {
				contains = false;
			}
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}

}
