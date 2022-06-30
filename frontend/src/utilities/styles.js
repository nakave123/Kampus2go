import {makeStyles} from '@mui/styles';

const useStyles = () =>  { return makeStyles({
    root: {
      backgroundColor: "#1f3b8f",
      color: "#ffffff",
      height: "3rem",
      lineHeight: 1,
      "&:hover": {
        backgroundColor: "transparent",
        border: "1px solid #1f3b8f",
        color: "#1f3b8f",
        height: "3rem",
        padding: 0,
      },
    },
  })};

  export default useStyles;