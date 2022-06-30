import { createSlice } from '@reduxjs/toolkit';
import axios from "axios";


const applicationsSlice = createSlice({
    name: 'applications',
    initialState : {applications:[]},
    reducers : {
        fetchApplications(state,action) {
            state.applications = action.payload.applications;
        },
        postApplication(state,action){
            state.applications.push(action.payload.application);
        }
    },
});

export const applicationsActions = applicationsSlice.actions;


export const fetchStudentApplications=(studentId)=>{
    return async (dispatch) =>{

            const getStudentApplications = async ()=>{
                const response = await axios.get(`http://localhost:9000/applications?studentId=${studentId}`);
                return response.data;
            }

            try {
              const applications = await getStudentApplications();
              dispatch(
                  applicationsActions.fetchApplications({
                      applications})
              );
            }
            catch(err){
                console.error(err);
            }
    }
}

export const postApplication=(application)=>{
    return async (dispatch) => {
        const postNewApplication = async()=>{
            const response = await axios({
                method: "POST",
                url: `http://localhost:9000/applications`,
                data: application,
                headers: {
                    "Content-Type": "application/json",
                    "Access-Control-Allow-Origin": "*",
                    accept: "*/*",
                  }
            });
            return response.data;
        }

        try {
            const application = await postNewApplication();
            dispatch(
                applicationsActions.postApplication({
                    application})
            );
          }
          catch(err){
              console.error(err);
          }

    }
}

export const applicationsReducer = applicationsSlice.reducer; 