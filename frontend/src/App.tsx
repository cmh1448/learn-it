import { useMemo } from "react";
import { useRoutes } from "react-router-dom";
import { routes } from "./routes/routes";

function App() {
  const currentRoute = useMemo(() => {
    return routes["logined"];
  }, []);
  const renderedRoute = useRoutes(currentRoute);

  return <>{renderedRoute}</>;
}

export default App;
