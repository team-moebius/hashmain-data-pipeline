import React from "react";
import EntryTab from "../organisms/EntryTab";

class EntryPage extends React.Component {
  public render() {
    return (
      <div className="inner-member">
        <div className="ly-member">
          <EntryTab />
        </div>
      </div>
    );
  }
}

export default EntryPage;
