import React from "react";
import { configureStore, createSlice } from "@reduxjs/toolkit";

// const initialAuthState = {isAuthenticated: true, loggedInStudent:[]};
//created auth-slice to maitain logged in user and authentcated state. Used redux-toolkit
const authSlice = createSlice({
  name: "authentication",
  initialState: { isAuthenticated: false, user: null },
  reducers: {
    login(state, action) {
      state.isAuthenticated = true;
      state.user = action.payload;
    },
    logout(state) {
      state.isAuthenticated = false;
    },
  },
});

export const authActions = authSlice.actions;

export const userSelector = (state) => state.user;

export default authSlice.reducer;
