import React, { useContext, useState } from "react";
import { Link as RouterLink } from "react-router-dom";
import {
  AppBar,
  Box,
  Toolbar,
  Typography,
  Button,
  Menu,
  MenuItem,
  IconButton,
  useMediaQuery,
  useTheme,
  Link,
} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import { UserContext } from "../App";
import { useNavigate } from "react-router-dom";

export default function Bar() {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down("md"));
  const history = useNavigate();
  const userContext = useContext(UserContext);
  const { userInfo, setUserInfo } = userContext;
  const isDriver = userInfo?.driver;
  const isLoggedIn = userInfo?.email;
  const [anchorEl, setAnchorEl] = useState(null);

  const handleMenu = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleLogout = () => {
    localStorage.removeItem("userInfo");
    setUserInfo(null);
    history("/");
  };

  const handleOpenMenu = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleCloseMenu = () => {
    setAnchorEl(null);
  };

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar>
          {isMobile ? (
            <>
              <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                Pool
              </Typography>
              <IconButton
                size="large"
                edge="start"
                color="inherit"
                aria-label="menu"
                sx={{ mr: 2 }}
                onClick={handleMenu}
              >
                <MenuIcon />
              </IconButton>
              <Menu
                id="menu-appbar"
                anchorEl={anchorEl}
                anchorOrigin={{
                  vertical: "top",
                  horizontal: "right",
                }}
                keepMounted
                transformOrigin={{
                  vertical: "top",
                  horizontal: "right",
                }}
                open={Boolean(anchorEl)}
                onClose={handleClose}
              >
                {isDriver && (
                  <MenuItem
                    onClick={handleClose}
                    component={RouterLink}
                    to="/create-pool"
                  >
                    Post a pool
                  </MenuItem>
                )}
                <MenuItem onClick={handleClose} component={RouterLink} to="/">
                  Join a pool
                </MenuItem>
                <MenuItem onClick={handleClose} component={RouterLink} to="/">
                  Home
                </MenuItem>
                {isLoggedIn ? (
                  <>
                    <MenuItem
                      onClick={handleClose}
                      component={RouterLink}
                      to="/my-pools"
                    >
                      My Pools
                    </MenuItem>
                    <MenuItem
                      onClick={handleClose}
                      component={RouterLink}
                      to="/my-crews"
                    >
                      My Crews
                    </MenuItem>

                    {isDriver ? (
                      <>
                        <MenuItem
                          onClick={handleClose}
                          component={RouterLink}
                          to="/passenger-profile"
                        >
                          Passenger Profile
                        </MenuItem>
                        <MenuItem
                          onClick={handleClose}
                          component={RouterLink}
                          to="/driver-profile"
                        >
                          Driver Profile
                        </MenuItem>
                      </>
                    ) : (
                      <MenuItem
                        onClick={handleClose}
                        component={RouterLink}
                        to="/passenger-profile"
                      >
                        My Profile
                      </MenuItem>
                    )}
                    <MenuItem onClick={handleLogout}>Logout</MenuItem>
                  </>
                ) : (
                  !isLoggedIn && (
                    <>
                      <MenuItem
                        onClick={handleClose}
                        component={RouterLink}
                        to="/signup"
                      >
                        Signup
                      </MenuItem>
                      <MenuItem
                        onClick={handleClose}
                        component={RouterLink}
                        to="/signin"
                      >
                        Login
                      </MenuItem>
                    </>
                  )
                )}
              </Menu>
            </>
          ) : (
            <>
              {isDriver && (
                <Button
                  component={RouterLink}
                  to="/create-pool"
                  sx={{
                    color: "#fff",
                    backgroundColor: "transparent",
                    fontWeight: "bold",
                    "&:hover": { background: "rgba(255,255,255,0.08)" },
                  }}
                >
                  Post a pool
                </Button>
              )}
              <Button
                component={RouterLink}
                to="/"
                color="inherit"
                sx={{
                  fontWeight: "bold",
                  color: "#fff",
                  "&:hover": { background: "rgba(255,255,255,0.08)" },
                }}
              >
                Join a pool
              </Button>
              <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                Pool
              </Typography>
              <Button
                component={RouterLink}
                to="/"
                sx={{
                  fontWeight: "bold",
                  color: "#fff",
                  "&:hover": { background: "rgba(255,255,255,0.08)" },
                }}
              >
                Home
              </Button>
              {isLoggedIn && (
                <Button
                  component={RouterLink}
                  to="/my-pools"
                  sx={{
                    color: "#fff",
                    fontWeight: "bold",
                    "&:hover": { background: "rgba(255,255,255,0.08)" },
                  }}
                >
                  My Pools
                </Button>
              )}
              {isLoggedIn && (
                <Button
                  component={RouterLink}
                  to="/my-crews"
                  sx={{
                    color: "#fff",
                    fontWeight: "bold",
                    "&:hover": { background: "rgba(255,255,255,0.08)" },
                  }}
                >
                  My Crews
                </Button>
              )}
              {isLoggedIn ? (
                <div>
                  <Button
                    color="inherit"
                    aria-controls="profile-menu"
                    aria-haspopup="true"
                    component={RouterLink}
                    to="/passenger-profile"
                    onClick={handleOpenMenu}
                    sx={{
                      fontWeight: "bold",
                      color: "#fff",
                      "&:hover": { background: "rgba(255,255,255,0.08)" },
                    }}
                  >
                    My Profile
                  </Button>

                  {isDriver && (
                    <Menu
                      id="profile-menu"
                      anchorEl={anchorEl}
                      keepMounted
                      open={Boolean(anchorEl)}
                      onClose={handleCloseMenu}
                    >
                      <MenuItem
                        onClick={handleClose}
                        component={RouterLink}
                        to="/passenger-profile"
                      >
                        Passenger Profile
                      </MenuItem>
                      <MenuItem
                        onClick={handleClose}
                        component={RouterLink}
                        to="/driver-profile"
                      >
                        Driver Profile
                      </MenuItem>
                    </Menu>
                  )}
                </div>
              ) : (
                <Button
                  component={RouterLink}
                  to="/signup"
                  sx={{
                    color: "#fff",
                    fontWeight: "bold",
                    "&:hover": { background: "rgba(255,255,255,0.08)" },
                  }}
                >
                  Signup
                </Button>
              )}
              {isLoggedIn ? (
                <Button
                  color="inherit"
                  onClick={handleLogout}
                  sx={{
                    fontWeight: "bold",
                    "&:hover": { background: "rgba(255,255,255,0.08)" },
                  }}
                >
                  Logout
                </Button>
              ) : (
                <Button
                  component={RouterLink}
                  to="/signin"
                  sx={{
                    color: "#fff",
                    fontWeight: "bold",
                    "&:hover": { background: "rgba(255,255,255,0.08)" },
                  }}
                >
                  Login
                </Button>
              )}
            </>
          )}
        </Toolbar>
      </AppBar>
    </Box>
  );
}
