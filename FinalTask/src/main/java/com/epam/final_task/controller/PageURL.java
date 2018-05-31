package com.epam.final_task.controller;

/**
 * Contains only paths at jsp pages.
 * @author Ihnat Mikhalkovich
 * @since 1.0
 */
public final class PageURL {

    public static final String INDEX_PAGE = "/";

    public static final String ERROR_PAGE = "/jsp/404.jsp";

    public static final String REQUEST_OFFERS_PAGE = "/FrontController?commandType=offers";

    public static final String REQUEST_PROFILE_PAGE = "/FrontController?commandType=profile";

    public static final String REQUEST_USERS_PAGE = "/FrontController?commandType=users&page=";

    public static final String EDIT_PROFILE_FORM_PAGE = "/WEB-INF/jsp/edit-profile-form.jsp";

    public static final String EDIT_ROOM_FORM_PAGE = "/WEB-INF/jsp/edit-room-form.jsp";

    public static final String HISTORY_PAGE = "/WEB-INF/jsp/history.jsp";

    public static final String OFFERS_PAGE = "jsp/offers.jsp";

    public static final String PROFILE_PAGE = "/WEB-INF/jsp/profile.jsp";

    public static final String RESERVATION_PAGE = "WEB-INF/jsp/reservation.jsp";

    public static final String REQUEST_HISTORY_PAGE = "/FrontController?commandType=history&page=1";

    public static final String USERS_PAGE = "/WEB-INF/jsp/users.jsp";

    public final static String ADD_ROOM_FORM_PAGE = "/WEB-INF/jsp/add-room-form.jsp";

    public final static String ORDERS_PAGE = "/WEB-INF/jsp/orders.jsp";

    public final static String REQUEST_ORDERS_PAGE = "/FrontController?commandType=orders&page=";

    public static final String TARIFFS_PAGE = "/WEB-INF/jsp/tariffs.jsp";

    public static final String REQUEST_TARIFFS_PAGE = "/FrontController?commandType=tariffs";

    public final static String ADD_TARIFF_FORM_PAGE = "/WEB-INF/jsp/add-tariff-form.jsp";

    public static final String EDIT_TARIFF_FORM_PAGE = "/WEB-INF/jsp/edit-tariff-form.jsp";

    public static final String ROOM_PAGE = "/WEB-INF/jsp/room.jsp";

    public static final String USER_HISTORY_PAGE = "/WEB-INF/jsp/user-history.jsp";

    private PageURL() {
    }

}
