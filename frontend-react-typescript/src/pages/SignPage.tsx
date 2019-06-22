import * as React from 'react';

import MuiTabs from '@material-ui/core/Tabs';
import MuiTab from '@material-ui/core/Tab';
import MuiTypography from '@material-ui/core/Typography';

import Paper from 'components/atoms/Paper';
import EntryTab from 'components/organisms/EntryTab';
import SignIn from 'components/templates/SignIn';

import 'assets/scss/pages/SignPage.scss';

interface SignPageProps {}
interface SignPageState {
  index: number;
}

// const SignPage: React.FC<SignPageProps> = props => (
//   <div className="inner-member">
//     <div className="ly-member">
//       <EntryTab />
//     </div>
//   </div>
// );

class SignPage extends React.Component<SignPageProps, SignPageState> {
  constructor(props: SignPageProps) {
    super(props);
    this.state = { index: 0 };
  }

  onChangeTabs = (event: React.ChangeEvent<{}>, value: any) => {
    this.setState({ index: value });
  };

  render() {
    return (
      <Paper className="sign-page" style={{ height: '100vh' }}>
        <Paper className="sign-page__contents" square>
          <MuiTabs
            centered
            indicatorColor="secondary"
            textColor="secondary"
            value={this.state.index}
            onChange={this.onChangeTabs}
          >
            <MuiTab
              label={
                <MuiTypography variant="h6" gutterBottom>
                  로그인
                </MuiTypography>
              }
            />
            <MuiTab
              label={
                <MuiTypography variant="h6" gutterBottom>
                  회원가입
                </MuiTypography>
              }
            />
          </MuiTabs>
          {this.state.index === 0 && <SignIn />}
        </Paper>
      </Paper>
    );
  }
}

export default SignPage;
