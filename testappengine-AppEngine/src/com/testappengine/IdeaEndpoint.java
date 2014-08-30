package com.testappengine;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;

@Api(name = "ideaendpoint", namespace = @ApiNamespace(ownerDomain = "testappengine.com", ownerName = "testappengine.com", packagePath = ""))
public class IdeaEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listIdea")
	public CollectionResponse<Idea> listIdea(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Idea> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Idea as Idea");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Idea>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Idea obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Idea> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getIdea")
	public Idea getIdea(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		Idea idea = null;
		try {
			idea = mgr.find(Idea.class, id);
		} finally {
			mgr.close();
		}
		return idea;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param idea the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertIdea")
	public Idea insertIdea(Idea idea) {
		EntityManager mgr = getEntityManager();
		try {
			mgr.persist(idea);
		} finally {
			mgr.close();
		}
		return idea;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param idea the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateIdea")
	public Idea updateIdea(Idea idea) {
		EntityManager mgr = getEntityManager();
		EntityTransaction transaction = mgr.getTransaction();
		Idea updatedIdea = new Idea();
		try {
			transaction.begin();
			updatedIdea = mgr.find(Idea.class, idea.getKey());
			if(idea.getUpvotesToAdd() != 0)
				updatedIdea.setUpvotes(updatedIdea.getUpvotes() + idea.getUpvotesToAdd());
			if(idea.getDownvotesToAdd() != 0)
				updatedIdea.setDownvotes(updatedIdea.getDownvotes() + idea.getDownvotesToAdd());
			if(idea.getPromotionsToAdd() != 0)
				updatedIdea.setPromotions(updatedIdea.getPromotions() + idea.getPromotionsToAdd());
			if(idea.getJunkToAdd() != 0)
				updatedIdea.setJunk(updatedIdea.getJunk() + idea.getJunkToAdd());
			if(updatedIdea.getJunk() > updatedIdea.getUpvotes()+1)
				mgr.remove(updatedIdea);
			if(idea.getComUserToAdd() != "" && idea.getComUserToAdd() != null)
				if(idea.getComToAddTo() != -1)
					updatedIdea.getComments().add(idea.getComToAddTo(), idea.getComUserToAdd());
				else
					updatedIdea.getComUsernames().add(idea.getComUserToAdd());
			if(idea.getComUpToAdd() != 0)
				if(idea.getComToAddTo() != -1)
					updatedIdea.setComUpvotes(idea.getComToAddTo(), updatedIdea.getComUpvotes().get(idea.getComToAddTo()) + idea.getComUpToAdd());
				else
					updatedIdea.getComUpvotes().add(idea.getComUpToAdd());
			if(idea.getComDownToAdd() != 0)
				if(idea.getComToAddTo() != -1)
					updatedIdea.getComDownvotes().set(idea.getComToAddTo(), updatedIdea.getComDownvotes().get(idea.getComToAddTo()) + idea.getComDownToAdd());
				else
					updatedIdea.getComUpvotes().add(idea.getComDownToAdd());
			if(idea.getComPromToAdd() != 0)
				if(idea.getComToAddTo() != -1)
					updatedIdea.getComPromotions().set(idea.getComToAddTo(), updatedIdea.getComPromotions().get(idea.getComToAddTo()) + idea.getComPromToAdd());
				else
					updatedIdea.getComUpvotes().add(idea.getComPromToAdd());
			if(idea.getComJunkToAdd() != 0)
				if(idea.getComToAddTo() != -1)
					updatedIdea.getComJunks().set(idea.getComToAddTo(), updatedIdea.getComJunks().get(idea.getComToAddTo()) + idea.getComJunkToAdd());
				else
					updatedIdea.getComUpvotes().add(idea.getComJunkToAdd());else
			//mgr.persist(updatedIdea);
			if(idea.getComToAdd() != "" && idea.getComToAdd() != null) {
				if(idea.getComToAddTo() != -1) {
					updatedIdea.getComments().add(idea.getComToAddTo(), idea.getComToAdd());
					updatedIdea.getComUpvotes().add(idea.getComToAddTo(), 0);
					updatedIdea.getComDownvotes().add(idea.getComToAddTo(), 0);
					updatedIdea.getComPromotions().add(idea.getComToAddTo(), 0);
					updatedIdea.getComJunks().add(idea.getComToAddTo(), 0);
				} else {
					updatedIdea.getComments().add(idea.getComToAdd());
					updatedIdea.getComUpvotes().add(0);
					updatedIdea.getComDownvotes().add(0);
					updatedIdea.getComPromotions().add(0);
					updatedIdea.getComJunks().add(0);
				}
			}
			transaction.commit();
			mgr.refresh(updatedIdea);
		} finally {
			mgr.close();
		}
		return idea;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeIdea")
	public void removeIdea(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			Idea idea = mgr.find(Idea.class, id);
			mgr.remove(idea);
		} finally {
			mgr.close();
		}
	}

	private boolean containsIdea(Idea idea) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Idea item = mgr.find(Idea.class, idea.getKey());
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
