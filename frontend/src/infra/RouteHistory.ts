import { History, createBrowserHistory } from 'history';

class RouteHistory {
  static instance: History = createBrowserHistory();

  get instance(): History {
    return RouteHistory.instance;
  }
}

export default RouteHistory;
