const destoryAll = (getID)=>{
    let destroyTag = document.getElementById(getID.toString());
    while (destroyTag.firstChild){
        destroyTag.removeChild(destroyTag.firstChild);
    }

}

export {destoryAll};