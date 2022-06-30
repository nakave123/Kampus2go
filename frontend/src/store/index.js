import { configureStore } from "@reduxjs/toolkit";

import authReducer from "./auth_slice";
import authSlice from "./auth_slice";
import { applicationsReducer } from "./applications_slice";
import organizationsSlice from "./org_slice";
import eventsSlice from "./event_slice";
import { registrationsReducer } from "./registrations_slice";

const store = configureStore({
  reducer: {
    auth: authSlice,
    organizations: organizationsSlice,
    events: eventsSlice,
    applications: applicationsReducer,
    registrations: registrationsReducer,
  },
});

export default store;
