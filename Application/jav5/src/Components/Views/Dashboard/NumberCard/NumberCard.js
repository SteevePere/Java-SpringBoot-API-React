import React from 'react';
import { Card, Space } from 'antd';


const NumberCard = (props) =>
{

	const {
		icon,
		title,
		align,
		number,
		delta,
		color,
		coverImage,
		backgroundPosition,
	} = props;


	return (

		<Card
			className='analyticsSmallCard'
			cover={
				<div
					style={{
						height: 40,
						backgroundImage: 'url(' + window.location.origin + '/' + coverImage +')',
						backgroundSize: 'cover',
						backgroundRepeat: 'no-repeat',
						backgroundPosition: backgroundPosition,
						boxShadow: 'inset 0 0 0 200px rgba(255, 255, 255, 0.2)',
					}}
				/>
			}
		>
			<div style={{ textAlign: align }}>
				<p>
					<Space>
						{icon}
						{title}
					</Space>
				</p>
				<h2 style={{ color: color }}>
					{number}
				</h2>
				{delta &&
				<p style={{ color: delta > 0 ? '#46bb20' : '#ff6666' }}>

					{delta > 0 && '+'}{delta} %
				</p>}
			</div>
		</Card>
	);
}


export default NumberCard;
