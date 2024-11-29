import { TYPE_COLORS } from "./Configuration.js";

// Convert EPSG:3857 to EPSG:4326
function epsg3857ToEpsg4326(coordinates) {
    const x = (coordinates[0] * 180) / 20037508.34;
    let y = (coordinates[1] * 180) / 20037508.34;
    y = (Math.atan(Math.exp(y * (Math.PI / 180))) * 360) / Math.PI - 90;
    return [x, y];
}

// Fetch data from form
export function fetchData(formData) {
    return fetch('/submit-cities', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        return data.list_result;
    });
}

// Round Flag Vietnam!
const starIcon = L.divIcon({
    className: 'custom-star-icon',
    html: '<i class="fas fa-star" style="color: yellow; font-size: 12px; border: 1px solid red; border-radius: 10px; background-color: red; position: relative; top: -8px;"></i>',
    iconSize: [20, 20],
    iconAnchor: [10, 10]
});

// Add location labels to specific layer
export function addLabelsToLayer(map, locations){
    locations.forEach(location => {
        L.marker([location.lat, location.lon], {
            icon: starIcon,
            zIndexOffset: 1000
        }).addTo(map);

        L.marker([location.lat, location.lon], {
            icon: L.divIcon({
                className: 'location-label',
                html: `<div>${location.name}</div>`,
                iconSize: [120, 40],
                iconAnchor: [60, 0]
            }),
            interactive: false
        }).addTo(map);
    });
}

export function renderPolylines(coordinateLayerGroup, listResult) {
    coordinateLayerGroup.clearLayers();

    const pixiContainer = new PIXI.Container();
    let firstDraw = true;
    let prevZoom;
    
    // Pre-load all icon textures
    const iconTextures = {};
    const iconLoadPromises = listResult.map(item => {
        const iconPath = `/images/legends/${item.type_struct}.png`;
        return PIXI.Assets.load(iconPath)
            .then(texture => {
                iconTextures[item.type_struct] = texture;
            })
            .catch(err => {
                console.warn(`Failed to load icon for ${item.type_struct}:`, err);
            });
    });

    Promise.all(iconLoadPromises).then(() => {
        const pixiOverlay = L.pixiOverlay((utils) => {
            const map = utils.getMap();
            const zoom = map.getZoom();
            const container = utils.getContainer();
            const renderer = utils.getRenderer();
            const project = utils.latLngToLayerPoint;
            const scale = utils.getScale();

            if (firstDraw || prevZoom !== zoom) {
                container.removeChildren();
                listResult.forEach(item => {
                    const typeColor = TYPE_COLORS[item.type_struct] || '#808080';
                    const colorHex = parseInt(typeColor.replace('#', '0x'), 16);

                    if (item.list_minifeature) {
                        item.list_minifeature.forEach(minifeature => {
                            if (minifeature.feature && minifeature.feature.geometry) {
                                const coordinates = minifeature.feature.geometry.coordinates;
                                const projectedCoords = coordinates.map(coord => {
                                    const [lon, lat] = epsg3857ToEpsg4326(coord);
                                    return project([lat, lon]);
                                });

                                // Draw line
                                const graphics = new PIXI.Graphics();
                                graphics.lineStyle(3 / scale, colorHex, 1);

                                projectedCoords.forEach((point, index) => {
                                    if (index === 0) {
                                        graphics.moveTo(point.x, point.y);
                                    } else {
                                        graphics.lineTo(point.x, point.y);
                                    }
                                });

                                container.addChild(graphics);

                                const centerIndex = Math.floor(coordinates.length / 2);
                                const [centerLon, centerLat] = epsg3857ToEpsg4326(coordinates[centerIndex]);
                                const centerPoint = project([centerLat, centerLon]);

                                // Add pre-loaded icon with interaction
                                const texture = iconTextures[item.type_struct];
                                if (texture) {
                                    const sprite = new PIXI.Sprite(texture);
                                    sprite.anchor.set(0.5);
                                    sprite.x = centerPoint.x;
                                    sprite.y = centerPoint.y;
                                    sprite.width = 18 / scale;
                                    sprite.height = 18 / scale;
                                    
                                    // Make sprite interactive
                                    sprite.interactive = true;
                                    sprite.buttonMode = true;

                                    // Add hit area to make clicking easier
                                    const hitArea = new PIXI.Circle(0, 0, 70);
                                    sprite.hitArea = hitArea;



                                    // Add click handler
                                    sprite.on('pointerdown', (e) => {
                                        if (minifeature.feature.properties) {
                                            const props = minifeature.feature.properties;
                                            const popupContent = createPopupContent(props, minifeature.image);
                                            
                                            L.popup({
                                                maxWidth: 320,
                                                maxHeight: 500,
                                                autoPan: true,
                                                className: 'custom-popup',
                                            })
                                            .setLatLng([centerLat, centerLon])
                                            .setContent(popupContent)
                                            .openOn(map);

                                        }
                                    });


                                    container.addChild(sprite);


                                }
                            }
                        });
                    }
                });
            }

            firstDraw = false;
            prevZoom = zoom;
            renderer.render(container);
            // redraw
        }, pixiContainer);


        pixiOverlay.addTo(coordinateLayerGroup);
    });
}

function createPopupContent(props, image) {
    const images = image?.sourceImagesList || [];
    const listVideo = image?.url_video;

    return `
        <div style="max-width: 300px;">
            <strong style="font-size: 14px;">${props.tennhan || 'No name'}</strong><br>
            ${createImageCarousel(images)}
            ${createVideoPlayer(listVideo)}
            <div style="margin-top: 8px;">
                <strong style="font-size: 15px; text-align: center; padding: 2px; background-color: rgba(255,0,0,0.2);">CHI TIẾT:</strong><br>
                ${props.chieudai ? `<strong>Chiều dài</strong>: ${props.chieudai} m<br>` : ''}
                ${props.thoigian ? `<strong>Thời gian</strong>: ${props.thoigian}<br>` : ''}
                ${props.tacdong ? `<strong>Tác động</strong>: ${props.tacdong}<br>` : ''}
                ${props.giaiphap ? `<strong>Giải pháp</strong>: ${props.giaiphap}<br>` : ''}
                ${props.thuocsong ? `<strong>Thuộc sông</strong>: ${props.thuocsong}<br>` : ''}
                ${props.xaref ? `<strong>Xã</strong>: ${props.xaref}<br>` : ''}
                ${props.huyenref ? `<strong>Huyện</strong>: ${props.huyenref}<br>` : ''}
                ${props.ghichu ? `<strong>Ghi chú</strong>: ${props.ghichu}<br>` : ''}
                ${props.giaiphap ? `<strong>Giải pháp</strong>: ${props.giaiphap}` : ''}
            </div>
        </div>`;
}

function createImageCarousel(images){
    if (!images || images.length === 0) return '';

    const carouselId = `carousel-${Math.random().toString(36).substring(2, 9)}`;

    let carouselHtml = `
                <div id="${carouselId}" class="image-carousel" style="position: relative; margin: 8px 0;">
                    <div class="carousel-container" style="position: relative; width: 100%; height: 200px;">
                `;

    images.forEach((imgUrl, index) => {
        carouselHtml += `
                    <div class="carousel-slide" style="display: ${index === 0 ? 'block' : 'none'}; position: absolute; width: 100%; height: 100%;">
                        <img src="${imgUrl}" 
                             style="width: 100%; 
                                    height: 100%; 
                                    object-fit: cover; 
                                    border-radius: 4px;"
                             alt="Image ${index + 1}"
                             onerror="this.parentElement.style.display='none'">
                    </div>
                `;
    });

    if (images.length > 1) {
        carouselHtml += `
                    <button onclick="changeSlide('${carouselId}', -1)" 
                            style="position: absolute; left: 5px; top: 50%; transform: translateY(-50%); 
                                   z-index: 2; background: rgba(0,0,0,0.5); color: white; 
                                   border: none; border-radius: 50%; width: 30px; height: 30px; 
                                   cursor: pointer;">←</button>
                    <button onclick="changeSlide('${carouselId}', 1)" 
                            style="position: absolute; right: 5px; top: 50%; transform: translateY(-50%); 
                                   z-index: 2; background: rgba(0,0,0,0.5); color: white; 
                                   border: none; border-radius: 50%; width: 30px; height: 30px; 
                                   cursor: pointer;">→</button>
                    <div style="position: absolute; bottom: 10px; left: 50%; transform: translateX(-50%); 
                              z-index: 2; color: white; background: rgba(0,0,0,0.5); 
                              padding: 2px 8px; border-radius: 10px;">
                        1/${images.length}
                    </div>
                `;
    }

    carouselHtml += `
                    </div>
                </div>
            `;

    return carouselHtml;
}

function createVideoPlayer(videoUrl) {
    if (!videoUrl) return '';
    const video = videoUrl.split(',')[0];
    const videoId = getId(video);
    const url ="https://www.youtube.com/embed/" + videoId;
    return `
                <div class="video-container" style="margin: 8px 0;">
                    <iframe src="${url}"></iframe>
                </div>
            `;
            
}

function getId(url) {
    const regExp = /^.*(youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|&v=)([^#&?]*).*/;
    const match = url.match(regExp);
    return (match && match[2].length === 11) ? match[2] : null;
}