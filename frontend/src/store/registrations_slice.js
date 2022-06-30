import { createSlice } from "@reduxjs/toolkit";
import axios from "axios";

const registrationsSlice = createSlice({
  name: "registrations",
  initialState: { registrations: [] },
  reducers: {
    fetchRegistrations(state, action) {
      state.registrations = action.payload.registrations;
    },
    postRegistration(state, action) {
      state.registrations.push(action.payload.registration);
    },
  },
});

export const registrationsActions = registrationsSlice.actions;

export const fetchStudentRegistrations = (studentId) => {
  return async (dispatch) => {
    const getStudentRegistrations = async () => {
      const response = await axios.get(
        `http://localhost:9000/registrations?studentId=${studentId}`
      );
      return response.data;
    };

    try {
      const registrations = await getStudentRegistrations();
      dispatch(
        registrationsActions.fetchRegistrations({
          registrations,
        })
      );
    } catch (err) {
      console.error(err);
    }
  };
};

export const postRegistration = (registration) => {
  return async (dispatch) => {
    const postNewRegistration = async () => {
      const response = await axios({
        method: "POST",
        url: `http://localhost:9000/registrations`,
        data: registration,
        headers: {
          "Content-Type": "application/json",
          "Access-Control-Allow-Origin": "*",
          accept: "*/*",
        },
      });
      return response.data;
    };

    try {
      const registration = await postNewRegistration();
      dispatch(
        registrationsActions.postRegistration({
          registration,
        })
      );
    } catch (err) {
      console.error(err);
    }
  };
};

export const registrationsReducer = registrationsSlice.reducer;
