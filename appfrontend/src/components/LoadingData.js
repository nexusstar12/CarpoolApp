import { Backdrop, CircularProgress, Typography, Box } from "@mui/material";

export const LoadingBackdrop = () => {
  return (
    <Backdrop
      open={true}
      sx={{ color: "#fff", zIndex: (theme) => theme.zIndex.drawer + 1 }}
    >
      <Box display="flex" flexDirection="column" alignItems="center">
        <CircularProgress color="inherit" />
        <Typography mt={2}>Loading data...</Typography>
      </Box>
    </Backdrop>
  );
};
