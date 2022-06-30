import { createSlice } from "@reduxjs/toolkit";
import axios from "axios";

const organizationsSlice = createSlice({
  name: "organizations",
  initialState: { organization: {}, organizations: [] },
  reducers: {
    fetchAnOrganization(state, action) {
      state.organization = action.payload.organization;
    },
    fetchAllOrganizations(state, action) {
      state.organizations = action.payload.organizations;
    },
    addOrganization(state, action) {
      state.organizations = [
        ...state.organizations,
        action.payload.organization,
      ];
    },
  },
});

export const organizationsActions = organizationsSlice.actions;

export const fetchAnOrganization = (orgId) => {
  return async (dispatch) => {
    const getAnOrganization = async () => {
      const response = await axios.get(
        `http://localhost:9000/organizations/${orgId}`
      );
      return response.data;
    };

    try {
      const organization = await getAnOrganization();
      dispatch(
        organizationActions.fetchAnOrganization({
          organization,
        })
      );
    } catch (err) {
      console.error(err);
    }
  };
};

export const fetchAllOrganizations = () => {
  return async (dispatch) => {
    const getAllOrganizations = async () => {
      const response = await axios.get(`http://localhost:9000/organizations/`);
      return response.data;
    };

    try {
      const organizations = await getAllOrganizations();
      dispatch(
        organizationActions.fetchAllOrganizations({
          organizations,
        })
      );
    } catch (err) {
      console.error(err);
    }
  };
};

export const addOrganization = (org) => {
  return async (dispatch) => {
    const addAOrganization = async () => {
      const response = await axios({
        method: "POST",
        url: "http://localhost:9000/organizations",
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
      const organization = await addAOrganization({ org });
      dispatch(
        organizationsActions.addOrganization({
          organization,
        })
      );
    } catch (err) {
      console.error(err);
    }
  };
};

export const organizationActions = organizationsSlice.actions;

export const orgSelector = (state) => state.organization;
export const allOrgSelector = (state) => state.organizations;

export default organizationsSlice.reducer;
