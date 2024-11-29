import { 
    WINDY_API_KEY,
    PROVINCES_GEOJSON,
    LOCATON_LABELS,
    SATELLITE_LAYER,
} from "./Configuration.js";

import {
    fetchData,
    addLabelsToLayer,
    renderPolylines
} from "./FunctionsForMap.js";

import {
    WATERWAYS_GEOJSON,
} from "./ThuyHe.js";

const options = {
    key: WINDY_API_KEY,
    verbose: true,
    lat: 21.028511,
    lon: 105.804817,
    zoom: 5,
};

let currentMapIndex = 0;
let currentMap = null;
let pixiOverlay = null;
let pixiContainer = null;

// Initialize PIXI Application
const app = new PIXI.Application({
    width: window.innerWidth,
    height: window.innerHeight,
    transparent: true,
    antialias: true
});

windyInit(options, windyAPI => {
    const { map, store} = windyAPI;
    map.options.maxZoom = 18;
    
    map.eachLayer(layer => {
        if (layer.options?.className?.includes('label')) {
            map.removeLayer(layer);
        }
    });
    
    // VIETNAM Label
    L.marker([18, 106], {
        icon: L.divIcon({
            className: 'location-label',
            html: `<div style="color: white;
                                white-space: nowrap;
                                font-size: 15px;
                                letter-spacing: 3px;
                                opacity: 0.5;
                                text-align: center;">VIỆT NAM</div>`,
            iconSize: [120, 40],
            iconAnchor: [60, 0]
        }),
        interactive: false
    }).addTo(map);

    // Add Hoang Sa Truong Sa labels
    addLabelsToLayer(map, LOCATON_LABELS.islandlabel);

    let labelLayerGroup = L.layerGroup().addTo(map);

    map.on('zoomend', () => {
        const currentZoom = map.getZoom();
        labelLayerGroup.clearLayers();
        if (currentZoom >= 4) {
            addLabelsToLayer(labelLayerGroup, LOCATON_LABELS.labellevel1);
        }
        if (currentZoom >= 6) {
            addLabelsToLayer(labelLayerGroup, LOCATON_LABELS.labellevel2);
        }
    });
    
    const toggleButton = document.getElementById('mapToggle');
    const toggleIcon = toggleButton.querySelector('i');

    const coordinateLayerGroup = L.layerGroup().addTo(map);

    function createPixiOverlay() {
        pixiContainer = new PIXI.Container();
        
        const pixiOverlay = L.pixiOverlay((utils) => {
            const renderer = utils.getRenderer();
            const project = utils.latLngToLayerPoint;
            const scale = utils.getScale();
            const zoom = map.getZoom();

            pixiContainer.removeChildren();

            const thuyHeCheckbox = document.getElementById('thuyhe');
            if (thuyHeCheckbox && thuyHeCheckbox.checked){
                WATERWAYS_GEOJSON.features.forEach(feature => {
                    const graphics = new PIXI.Graphics();
                    const coordinates = feature.geometry.coordinates;
                    const type = feature.geometry.type;
                    const strokeColor = '0x4cc9f0';

                    graphics.lineStyle(1 / scale, strokeColor, 1);
                    
                    coordinates.forEach(line => {
                        const points = line.map(coord => {
                            const point = project([coord[1], coord[0]]);
                            return new PIXI.Point(point.x, point.y);
                        });
                        graphics.moveTo(points[0].x, points[0].y);
                        points.forEach((point, index) => {
                            if (index > 0) {
                                graphics.lineTo(point.x, point.y);
                            }
                        });
                    });
                    

                    pixiContainer.addChild(graphics);
                });
            }


            PROVINCES_GEOJSON.features.forEach(feature => {
                const graphics = new PIXI.Graphics();
                const coordinates = feature.geometry.coordinates;
                const provinceName = feature.properties.ten_tinh;

                const strokeColor = (currentMap === SATELLITE_LAYER) ? 0xFFFFFF : 0x000000;
                graphics.lineStyle(1 / scale, strokeColor, 1);
                graphics.beginFill(0xFFFFFF, 0);

                coordinates.forEach(polygon => {
                    const rings = polygon;
                    
                    rings.forEach(ring => {
                        const points = ring.map(coord => {
                            const point = project([coord[1], coord[0]]);
                            return new PIXI.Point(point.x, point.y);
                        });
                        
                        if (points.length > 0) {
                            graphics.moveTo(points[0].x, points[0].y);
                            points.forEach((point, index) => {
                                if (index > 0) {
                                    graphics.lineTo(point.x, point.y);
                                }
                            });
                            graphics.closePath();
                        }
                    });
                });

                graphics.endFill();

                pixiContainer.addChild(graphics);
                

                const ranhGioiTinhCheckbox = document.getElementById('ranhgioitinh')

                if (ranhGioiTinhCheckbox && ranhGioiTinhCheckbox.checked){
                    if (zoom >= 7 && provinceName){
                        if (feature.geometry.centroid){
                            if (feature.geometry.level === 1){
                                const centroid = feature.geometry.centroid;
                                const labelPoint = project([centroid[1], centroid[0]]);
            
                                const label = new PIXI.Text(provinceName, {
                                    fontFamily: 'NotoSansJP',
                                    fontWeight: 'bold',
                                    fontSize: 13 / scale,
                                    fill: 0xffffff,
                                    align: 'center'
                                });
            
                                label.x = labelPoint.x;
                                
                                label.y = labelPoint.y;
                                label.anchor.set(0.5);
            
                                pixiContainer.addChild(label);
                            }
                            else if (feature.geometry.level === 2 && zoom >= 8){
                                const centroid = feature.geometry.centroid;
                                const labelPoint = project([centroid[1], centroid[0]]);
            
                                const label = new PIXI.Text(provinceName, {
                                    fontFamily: 'NotoSansJP',
                                    fontWeight: 'bold',
                                    fontSize: 13 / scale,
                                    fill: 0xffffff,
                                    align: 'center'
                                });
            
                                label.x = labelPoint.x;
                                
                                label.y = labelPoint.y;
                                label.anchor.set(0.5);
            
                                pixiContainer.addChild(label);
                            }
                            
                        }
    
                    }
                }
            
                
            });

            renderer.render(pixiContainer);
        }, pixiContainer, {
            padding: 0.5,
            resolution: window.devicePixelRatio || 1,
            autoPreventDefault: true,
            preserveDrawingBuffer: true
        });
        return pixiOverlay;
    }

    function updateMapView() {
        // Remove existing PixiOverlay
        if (pixiOverlay) {
            map.removeLayer(pixiOverlay);
        }

        // Remove existing base map
        if (currentMap) {
            map.removeLayer(currentMap);
        }

        switch(currentMapIndex) {
            case 0: // Windy
                toggleIcon.className = 'fas fa-wind';
                store.set('overlay', 'wind');
                currentMap = null;
                break;
            case 1: // Satellite
                currentMap = SATELLITE_LAYER;
                SATELLITE_LAYER.addTo(map);
                toggleIcon.className = 'fas fa-satellite';
                break;
        }

        // Create and add new PixiOverlay
        pixiOverlay = createPixiOverlay();
        pixiOverlay.addTo(map);

        // Ensure proper layer ordering
        if (coordinateLayerGroup) {
            map.removeLayer(coordinateLayerGroup);
            coordinateLayerGroup.addTo(map);
        }
    }

    toggleButton.addEventListener('click', () => {
        currentMapIndex = (currentMapIndex + 1) % 2;
        updateMapView();
    });

    // Initialize first map view
    updateMapView();

    // Set up form submission handler
    document.getElementById('requestAPI').addEventListener('submit', function(event) {
        event.preventDefault();
        const formData = new FormData(this);
        
        fetchData(formData)
            .then(listResult => {
                console.log('List result:', listResult);
                renderPolylines(coordinateLayerGroup, listResult);
            })
            .catch(error => {
                console.error('Error fetching data:', error);
            });
    });

    // Sliding image carousel
    window.changeSlide = function(carouselId, direction) {
        const carousel = document.getElementById(carouselId);
        const slides = carousel.getElementsByClassName('carousel-slide');
        let activeIndex = Array.from(slides).findIndex(slide => slide.style.display === 'block');
        
        slides[activeIndex].style.display = 'none';
        
        activeIndex += direction;
        if (activeIndex >= slides.length) activeIndex = 0;
        if (activeIndex < 0) activeIndex = slides.length - 1;
        
        slides[activeIndex].style.display = 'block';
        
        const counter = carousel.querySelector('div[style*="bottom: 10px"]');
        if (counter) {
            counter.textContent = `${activeIndex + 1}/${slides.length}`;
        }
    };
});

