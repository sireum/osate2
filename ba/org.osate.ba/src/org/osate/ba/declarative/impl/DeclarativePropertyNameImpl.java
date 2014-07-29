/**
 * AADL-BA-FrontEnd
 * 
 * Copyright © 2011 TELECOM ParisTech and CNRS
 * 
 * TELECOM ParisTech/LTCI
 * 
 * Authors: see AUTHORS
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the Eclipse Public License as published by Eclipse, either
 * version 1.0 of the License, or (at your option) any later version. This
 * program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the Eclipse Public License for
 * more details. You should have received a copy of the Eclipse Public License
 * along with this program. If not, see
 * http://www.eclipse.org/org/documents/epl-v10.php
 */
package org.osate.ba.declarative.impl ;

import java.util.Collection ;
import org.eclipse.emf.common.notify.Notification ;
import org.eclipse.emf.common.notify.NotificationChain ;

import org.eclipse.emf.common.util.EList ;
import org.eclipse.emf.ecore.EClass ;
import org.eclipse.emf.ecore.InternalEObject ;

import org.eclipse.emf.ecore.impl.ENotificationImpl ;

import org.eclipse.emf.ecore.util.EObjectContainmentEList ;
import org.eclipse.emf.ecore.util.InternalEList ;
import org.osate.ba.aadlba.IntegerValue ;
import org.osate.ba.aadlba.PropertyField ;

import org.osate.ba.declarative.DeclarativePackage ;
import org.osate.ba.declarative.DeclarativePropertyName ;
import org.osate.ba.declarative.Identifier ;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property Name</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.osate.ba.declarative.impl.DeclarativePropertyNameImpl#getPropertyName <em>Property Name</em>}</li>
 *   <li>{@link org.osate.ba.declarative.impl.DeclarativePropertyNameImpl#getFields <em>Fields</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DeclarativePropertyNameImpl extends DeclarativeBehaviorElementImpl
                                                                               implements
                                                                               DeclarativePropertyName
{
  /**
   * The cached value of the '{@link #getPropertyName() <em>Property Name</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPropertyName()
   * @generated
   * @ordered
   */
  protected Identifier propertyName ;

  /**
   * The cached value of the '{@link #getFields() <em>Fields</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFields()
   * @generated
   * @ordered
   */
  protected EList<PropertyField> fields ;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected DeclarativePropertyNameImpl()
  {
    super() ;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return DeclarativePackage.Literals.DECLARATIVE_PROPERTY_NAME ;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Identifier getPropertyName()
  {
    return propertyName ;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetPropertyName(Identifier newPropertyName,
                                                NotificationChain msgs)
  {
    Identifier oldPropertyName = propertyName ;
    propertyName = newPropertyName ;
    if(eNotificationRequired())
    {
      ENotificationImpl notification =
            new ENotificationImpl(this, Notification.SET,
                  DeclarativePackage.DECLARATIVE_PROPERTY_NAME__PROPERTY_NAME,
                  oldPropertyName, newPropertyName) ;
      if(msgs == null)
        msgs = notification ;
      else
        msgs.add(notification) ;
    }
    return msgs ;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPropertyName(Identifier newPropertyName)
  {
    if(newPropertyName != propertyName)
    {
      NotificationChain msgs = null ;
      if(propertyName != null)
        msgs =
              ((InternalEObject) propertyName)
                    .eInverseRemove(this,
                                    EOPPOSITE_FEATURE_BASE -
                                          DeclarativePackage.DECLARATIVE_PROPERTY_NAME__PROPERTY_NAME,
                                    null, msgs) ;
      if(newPropertyName != null)
        msgs =
              ((InternalEObject) newPropertyName)
                    .eInverseAdd(this,
                                 EOPPOSITE_FEATURE_BASE -
                                       DeclarativePackage.DECLARATIVE_PROPERTY_NAME__PROPERTY_NAME,
                                 null, msgs) ;
      msgs = basicSetPropertyName(newPropertyName, msgs) ;
      if(msgs != null)
        msgs.dispatch() ;
    }
    else if(eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET,
            DeclarativePackage.DECLARATIVE_PROPERTY_NAME__PROPERTY_NAME,
            newPropertyName, newPropertyName)) ;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<PropertyField> getFields()
  {
    if(fields == null)
    {
      fields =
            new EObjectContainmentEList.Unsettable<PropertyField>(
                  PropertyField.class, this,
                  DeclarativePackage.DECLARATIVE_PROPERTY_NAME__FIELDS) ;
    }
    return fields ;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetFields()
  {
    if(fields != null)
      ((InternalEList.Unsettable<?>) fields).unset() ;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetFields()
  {
    return fields != null && ((InternalEList.Unsettable<?>) fields).isSet() ;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd,
                                          int featureID,
                                          NotificationChain msgs)
  {
    switch ( featureID )
    {
      case DeclarativePackage.DECLARATIVE_PROPERTY_NAME__PROPERTY_NAME :
        return basicSetPropertyName(null, msgs) ;
      case DeclarativePackage.DECLARATIVE_PROPERTY_NAME__FIELDS :
        return ((InternalEList<?>) getFields()).basicRemove(otherEnd, msgs) ;
    }
    return super.eInverseRemove(otherEnd, featureID, msgs) ;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID,
                     boolean resolve,
                     boolean coreType)
  {
    switch ( featureID )
    {
      case DeclarativePackage.DECLARATIVE_PROPERTY_NAME__PROPERTY_NAME :
        return getPropertyName() ;
      case DeclarativePackage.DECLARATIVE_PROPERTY_NAME__FIELDS :
        return getFields() ;
    }
    return super.eGet(featureID, resolve, coreType) ;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID,
                   Object newValue)
  {
    switch ( featureID )
    {
      case DeclarativePackage.DECLARATIVE_PROPERTY_NAME__PROPERTY_NAME :
        setPropertyName((Identifier) newValue) ;
        return ;
      case DeclarativePackage.DECLARATIVE_PROPERTY_NAME__FIELDS :
        getFields().clear() ;
        getFields().addAll((Collection<? extends PropertyField>) newValue) ;
        return ;
    }
    super.eSet(featureID, newValue) ;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch ( featureID )
    {
      case DeclarativePackage.DECLARATIVE_PROPERTY_NAME__PROPERTY_NAME :
        setPropertyName((Identifier) null) ;
        return ;
      case DeclarativePackage.DECLARATIVE_PROPERTY_NAME__FIELDS :
        unsetFields() ;
        return ;
    }
    super.eUnset(featureID) ;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch ( featureID )
    {
      case DeclarativePackage.DECLARATIVE_PROPERTY_NAME__PROPERTY_NAME :
        return propertyName != null ;
      case DeclarativePackage.DECLARATIVE_PROPERTY_NAME__FIELDS :
        return isSetFields() ;
    }
    return super.eIsSet(featureID) ;
  }

} //DeclarativePropertyNameImpl
