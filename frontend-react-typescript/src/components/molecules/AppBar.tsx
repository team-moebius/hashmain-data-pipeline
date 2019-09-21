import * as React from 'react';
import classNames from 'classnames';

import { makeStyles } from '@material-ui/core/styles';

import Text from 'components/atoms/Text';
import Header, { HeaderProps } from 'components/atoms/Header';

interface AppBarChildSlots {
  left: React.ReactChild;
  right: React.ReactChild;
}

export interface AppBarProps extends HeaderProps {
  children?: AppBarChildSlots;
  subTitle?: JSX.Element;
}

const useStyles = makeStyles(theme => ({
  root: { flexGrow: 1 },
  titleWrapper: { flexGrow: 1 },
  title: {
    fontWeight: 'bold',
  },
}));

const AppBar: React.FC<AppBarProps> = props => {
  const { className, children, subTitle, title, ...rest } = props;
  const classes = useStyles();
  return (
    <Header className={classNames(classes.root, className)} {...rest}>
      {children && children.left}
      <div className={classes.titleWrapper}>
        <Text className={classes.title} variant="subtitle1">
          <em>{title}</em>
        </Text>
        <Text variant="subtitle2">{subTitle}</Text>
      </div>
      {children && children.right}
    </Header>
  );
};

export default AppBar;
