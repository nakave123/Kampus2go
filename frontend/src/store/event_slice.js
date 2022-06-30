import { createSlice } from "@reduxjs/toolkit";
import axios from "axios";

const eventsSlice = createSlice({
  name: "events",
  initialState: { event: {}, events: [] },
  reducers: {
    fetchAllEvents(state, action) {
      state.events = action.payload.events;
    },
    fetchAnEvent(state, action) {
      state.event = action.payload.event;
    },
    addEvent(state, action) {
      state.events = [...state.events, action.payload.event];
    },
  },
});

export const eventsActions = eventsSlice.actions;

export const fetchAllEvents = () => {
  return async (dispatch) => {
    const getAllEvents = async () => {
      const response = await axios.get("http://localhost:9000/events");
      return response.data;
    };

    try {
      const events = await getAllEvents();
      dispatch(eventsActions.fetchAllEvents({ events }));
    } catch (err) {
      console.error(err);
    }
  };
};

export const fetchAnEvent = (eventId) => {
  return async (dispatch) => {
    const getAnEvent = async () => {
      const response = await axios.get(
        `http://localhost:9000/events/${eventId}`
      );
      return response.data;
    };

    try {
      const event = await getAnEvent();
      dispatch(
        eventsActions.fetchAnEvent({
          event,
        })
      );
    } catch (err) {
      console.error(err);
    }
  };
};

export const addEvent = (org) => {
  return async (dispatch) => {
    const addAnEvent = async () => {
      const response = await axios({
        method: "POST",
        url: "http://localhost:9000/events",
        data: org,
        headers: {
          "Content-Type": "application/json",
          "Access-Control-Allow-Origin": "*",
          accept: "*/*",
        },
        validateStatus: (status) => {
          return true;
        },
      }).catch((err) => console.log(err.data));

      return response.data;
    };

    try {
      const event = await addAnEvent({ org });
      dispatch(
        eventsActions.addEvent({
          event,
        })
      );
    } catch (err) {
      console.error(err);
    }
  };
};

export const eventAction = eventsSlice.actions;

export const anEventSelector = (state) => state.event;
export const allEventSelector = (state) => state.events;

export default eventsSlice.reducer;
