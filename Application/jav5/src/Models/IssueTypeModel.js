import React from 'react';
import {
	BulbTwoTone,
	BugTwoTone,
} from '@ant-design/icons';


export const IssueTypeModel = {
	STORY: {
		value: 'STORY',
		label: 'Story',
		icon: <BulbTwoTone twoToneColor='#52c41a'/>,
		chartColor: 'rgba(109, 191, 57, 0.6)',
	},
	BUG: {
		value: 'BUG',
		label: 'Bug',
		icon: <BugTwoTone twoToneColor='red'/>,
		chartColor: 'rgba(245, 56, 78, 0.6)',
	},
};
