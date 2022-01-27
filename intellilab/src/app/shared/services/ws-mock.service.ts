import { Injectable} from '@angular/core';
import * as lodash from "lodash";


@Injectable({providedIn:"root"})
export class MockDataService {

  designList = []
  instanceList = []




constructor(){}

saveDesign(design){
  if(this.designList.length > 0){
    const length = this.designList.length
    const lastIndex = length - 1
    const id = this.designList[lastIndex].templateId
    const newId = id + 1
    design.templateId = newId
  }else{
    design.templateId = 0
  }
  this.designList.push(design)
  console.log(`Mock service: new design is added.`)
}

saveInstance(instance){
  if(this.instanceList.length > 0){
    const length = this.instanceList.length
    const lastIndex = length - 1
    const id = this.instanceList[lastIndex].worksheetId
    const newId = id + 1
    instance.worksheetId = newId
  }else{
    instance.worksheetId = 0
  }
  this.instanceList.push(instance)
  console.log('Mock service: new instance is added')
}

updateDesign(designId, updateData){
  let found = false
  this.designList.forEach((design, index) => {
    if(design.templateId === designId){
      found = true
      this.designList.splice(index, 1, updateData)
      console.log(`Mock service: design ${index} is updated`)
    }
  })

  if(!found){
    console.log(`Mock service: design ${designId} is not found`)
  }
}

updateInstance(instanceId, updateData){
  let found = false
  this.instanceList.forEach((instance, index) => {
    if(instance.worksheetId === instanceId){
      found = true
      this.instanceList.splice(index, 1, updateData)
      console.log(`Mock service: instance ${index} is updated`)
    }
  })

  if(!found){
    console.log(`Mock service: instance ${instanceId} is not found`)
  }
}

removeDesign(designId){
  let found = false
  this.designList.forEach((design, index) => {
    if(design.templateId === designId){
      found = true
      this.designList.splice(index, 1)
      console.log(`Mock service: design ${index} is removed`)
    }
  })

  if(!found){
    console.log(`Mock service: design ${designId} is not found`)
  }
}

getDesignList(){
  const list = this.designList.map(design => {
    return {
      creationDate: design.creationDate,
      exObjective: design.exObjective,
      exType: design.exType,
      finalizationDate: design.finalizationDate,
      finalized: design.finalized,
      parentId: design.parentId,
      saveDate: design.saveDate,
      scientist: design.scientist,
      studyNum: design.studyNum,
      templateId: design.templateId,
      templateName: design.templateName,
      userId: design.userId,
      version: design.version
    }
  })

  console.log('Mock service: design list is returned')
  return lodash.cloneDeep(list)
}

getInstanceList(){
  const list = this.designList.map(instance => {
    return {
      analystId: instance.analystId,
      analystName: instance.analystName,
      completionDate: instance.completionDate,
      dateStr: instance.dateStr,
      exObjective: instance.exObjective,
      exType: instance.exType,
      reviewDate: instance.reviewDate,
      reviewerId: instance.reviewerId,
      reviewerName: instance.reviewerName,
      saveDate: instance.saveDate,
      startDate: instance.startDate,
      status: instance.status,
      studyNum: instance.studyNum,
      templateId: instance.templateId,
      worksheetId: instance.worksheetId,
      worksheetName: instance.worksheetName
    }
  })

  console.log('Mock service: instance list is returned')
  return lodash.cloneDeep(list)
}

createAssignment(assignment){
  let found = false
  this.designList.forEach(design => {
    if(design.templateId === assignment.templateId){
      found = true
      design.analysts = assignment.analysts
      console.log(`Mock service: assignment is created for design ${assignment.templateId}`)
    }
  })

  if(!found){
    console.log(`Mock service: failed to create assignment, design ${assignment.template} is not found`)
  }
}

getAssignmentListById(analystId){

  let assignList = []
  this.designList.forEach(design => {
    if(design.analysts){
      design.analysts.forEach(userId => {
        if(userId === analystId){
          assignList.push(design)
        }
      })
    }
  })

  return lodash.cloneDeep(assignList)
}


}//End of the file



