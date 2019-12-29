import { History, createBrowserHistory } from 'history';

class RouteHistory {
  private static instance: History = createBrowserHistory();

  public get instance(): History {
    return RouteHistory.instance;
  }
}

export default RouteHistory;
