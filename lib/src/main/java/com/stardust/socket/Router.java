package com.stardust.socket;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Stardust on 2016/10/15.
 */

public class Router {

    private static final String TAG = "Router";
    private boolean mIsStrictMode = false;

    public static class BeyondJurisdictionException extends RuntimeException {

        public BeyondJurisdictionException(int requestType, int requestLevel) {
            super("Cannot handle request type " + requestType + " at level " + requestLevel);
        }
    }

    public static final RequestHandler EMPTY_REQUEST_HANDLER = new RequestHandler() {
        @Override
        public void handle(Package pack) {

        }
    };

    private Map<Integer, RequestHandler> mHandlerMap = new HashMap<>();
    private Map<Integer, String> mRequestTypeNameMap = new TreeMap<>();
    private int mRequestLevel;

    public Router() {
        this(0, false);
    }

    public Router(boolean isStrictMode) {
        this(0, isStrictMode);
    }

    public Router(int requestLevel) {
        this(requestLevel, false);
    }

    public Router(int requestLevel, boolean isStrictMode) {
        mRequestLevel = requestLevel;
        mIsStrictMode = isStrictMode;
    }

    public Router(Router parent) {
        this(parent.mRequestLevel + 1, parent.mIsStrictMode);
    }

    public void use(int requestType, RequestHandler handler) {
        mHandlerMap.put(requestType, handler);
    }

    public void use(int requestType, String requestTypeName, RequestHandler handler) {
        mHandlerMap.put(requestType, handler);
        mRequestTypeNameMap.put(requestType, requestTypeName);
    }

    public void use(int requestType, Router subRouter) {
        use(requestType, new RouterRequestHandler(subRouter));
    }

    public void use(int requestType, String requestTypeName, Router subRouter) {
        use(requestType, requestTypeName, new RouterRequestHandler(subRouter));
    }


    public void handle(Package pack) {
        int requestType = pack.getRequestType(mRequestLevel);
        getRequestTypeName(requestType);
        RequestHandler handler = mHandlerMap.get(requestType);
        if (handler != null) {
            Log.i(TAG, "Handle request type " + requestType + "(" + getRequestTypeName(requestType) + ") at level " + mRequestLevel);
            handler.handle(pack);
            return;
        }
        if (mIsStrictMode) {
            throw new BeyondJurisdictionException(requestType, mRequestLevel);
        } else {
            Log.w(TAG, "Discard unknown request type " + requestType + " at level " + mRequestLevel);
        }
    }

    public String getRequestTypeName(int requestType) {
        String name = mRequestTypeNameMap.get(requestType);
        if (name == null)
            name = "UNKNOWN";
        return name;
    }

    private static class RouterRequestHandler implements RequestHandler {

        private Router mRouter;

        RouterRequestHandler(Router router) {
            if (router == null) {
                throw new NullPointerException("router == null");
            }
            mRouter = router;
        }

        @Override
        public void handle(Package pack) {
            mRouter.handle(pack);
        }
    }


}
