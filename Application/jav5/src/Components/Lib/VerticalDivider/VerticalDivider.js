import React from 'react';

import './VerticalDivider.css';


const VerticalDivider = (props) =>
{

	const {
		height,
	} = props;


	return (

		<hr
			className='verticalDivider'
			style={{
				height: height,
				color: '#e0e0e0'
			}}
		/>
    );
};


export default VerticalDivider;
