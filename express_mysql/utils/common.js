function Today(){
    var date = new Date();
    var year = date.getFullYear();
    var month = ("0" + (1 + date.getMonth())).slice(-2);
    var day = ("0" + date.getDate()).slice(-2);

    return year + "-" + month + "-" + day;
}
exports.getToday = function(){
    return Today();
}

exports.NewUser = function(req){
  return {
    email: req.body.email,
    name: req.body.name,
    sex: req.body.sex,
    age: req.body.age,
    score: req.body.score,
    profile: req.body.profile,
  }
}

exports.NewModel = function(req){
  return {
    id: req.body.id,
    file_name: req.body.file_name,
  }
}

exports.Newinfo = function(req){
  return {
    date: req.body.date,
    email: req.body.email,
    exername: req.body.name,
    goal: req.body.goal,
    current: 0,
    complete: 0,
  }
}
exports.Updateinfo = function(req){
  return [
    req.body.date,
    req.body.email,
    req.body.name, // 새로운 운동 이름 
    req.body.goal,
    req.body.prename, // 이전 운동 이름 // 두개가 같아도 됨 
  ]
}
